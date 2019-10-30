package org.dst.rpc.transport.codec;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import org.dst.rpc.core.codec.Codec;
import org.dst.rpc.core.codec.Serialization;
import org.dst.rpc.core.common.constants.CodecConstants;
import org.dst.rpc.core.common.constants.CodecConstants.DataType;
import org.dst.rpc.core.common.constants.CodecConstants.Version;
import org.dst.rpc.core.exception.CodecException;
import org.dst.rpc.core.utils.ReflectUtils;
import org.dst.rpc.transport.api.async.DefaultResponse;
import org.dst.rpc.transport.api.async.Request;
import org.dst.rpc.transport.api.async.Response;

/**
 * 编解码核心类：
 *
 * 协议头：
 *
 * |      magic 16bit     | version 8bit | type flag 8bit |
 * |               content length 32 bit                  |
 * |               request id     64 bit                  |
 * |               request id     64 bit                  |
 * |               content ...                            |
 *
 * todo: content length 放到后面
 */
public class DstCodec implements Codec {

  private Serialization serialization;

  public DstCodec(Serialization serialization) {
    this.serialization = serialization;
  }

  @Override
  public byte[] encode(Object message) throws CodecException {
    if (!(message instanceof Request) && !(message instanceof Response)) {
      throw new CodecException("Unsupported message type when encode: " + message.getClass());
    }

    try {
      if (message instanceof Request) {
        return encodeRequest((Request) message);
      } else {
        return encodeResponse((Response) message);
      }
    } catch (Exception e) {
      throw new CodecException("Encode error: ", e);
    }
  }

  @Override
  public Object decode(byte[] data) throws CodecException {
    int pos = 0;

    short magic = bytesToShort(data, pos);
    pos += 2;
    if (magic != CodecConstants.MAGIC_HEAD) {
      throw new CodecException("Magic error: " + magic);
    }

    byte version = data[pos++]; // 暂时不会用到，防止以后改协议。
    byte dataType = data[pos++];

    int contentLength = bytesToInt(data, pos);
    pos += 4;

    long requestId = bytesToLong(data, pos);
    pos += 8;

    byte[] content = new byte[contentLength];
    System.arraycopy(data, pos, content, 0, contentLength);

    try {
      switch (DataType.getDataTypeByValue(dataType)) {
        case VOID:
          return org.dst.rpc.core.common.Void.getInstance();
        case REQUEST:
          return decodeRequest(requestId, content);
        case RESPONSE:
          return decodeResponse(requestId, content);
        case EXCEPTION:
          return decodeException(requestId, content);
        default:
          throw new CodecException("Unknown data type: " + dataType);
      }
    } catch (Exception e) {
      throw new CodecException("Decode from 'byte[] content' error", e);
    }
  }


  private byte[] encodeRequest(Request request) throws Exception {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    ObjectOutput output = new ObjectOutputStream(outputStream);

    output.writeUTF(request.getInterfaceName());
    output.writeUTF(request.getMethodName());

    // if(request.getArgsType() != null && !"".equals(request.getArgsType())) {
    output.writeUTF(request.getArgsType());
    // }

    if (request.getArgsValue() != null && request.getArgsValue().length > 0) {
      for (Object obj : request.getArgsValue()) {
        output.writeObject(serialization.serialize(obj));
      }
    }

    output.flush();
    byte[] body = outputStream.toByteArray();
    output.close();

    return encode0(body, DataType.REQUEST, request.getRequestId());
  }

  private byte[] encodeResponse(Response response) throws Exception {
    DataType dataType;
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    ObjectOutput output = new ObjectOutputStream(outputStream);

    if (response.getException() != null) {
      output.writeUTF(response.getException().getClass().getName());
      output.writeObject(serialization.serialize(response.getException()));
      dataType = DataType.EXCEPTION;
    } else if (response.getValue() != null) {
      output.writeUTF(response.getValue().getClass().getName());
      output.writeObject(serialization.serialize(response.getValue()));
      dataType = DataType.RESPONSE;
    } else {
      dataType = DataType.VOID;
    }

    output.flush();
    byte[] body = outputStream.toByteArray();
    output.close();

    return encode0(body, dataType, response.getRequestId());
  }

  private byte[] encode0(byte[] body, DataType dataType, long requestId) throws IOException {
    byte[] header = new byte[CodecConstants.HEADER_LENGTH];
    int pos = 0;

    shortToBytes(header, pos, CodecConstants.MAGIC_HEAD);
    pos += 2;

    header[pos++] = Version.VERSION_1.getValue();
    header[pos++] = dataType.getValue();

    intToBytes(header, pos, body.length);
    pos += 4;

    longToBytes(header, pos, requestId);
    pos += 8;

    byte[] data = new byte[header.length + body.length];
    System.arraycopy(header, 0, data, 0, header.length);
    System.arraycopy(body, 0, data, pos, body.length);

    return data;
  }


  private Object decodeRequest(long requestId, byte[] content) throws Exception {
    ObjectInput input = new ObjectInputStream(new ByteArrayInputStream(content));
    String interfaceName = input.readUTF();
    String methodName = input.readUTF();
    String argsType = input.readUTF();

    Request request = new Request();
    request.setRequestId(requestId);

    request.setInterfaceName(interfaceName);
    request.setMethodName(methodName);
    request.setArgsType(argsType);
    request.setArgsValue(decodeRequestParameter(input, argsType));

    input.close();
    return request;
  }

  private Object decodeResponse0(long requestId, byte[] content, boolean isException)
      throws Exception {
    ObjectInput input = new ObjectInputStream(new ByteArrayInputStream(content));
    Response response = new DefaultResponse();

    String className = input.readUTF();
    Class<?> clz = ReflectUtils.forName(className);
    Object result = serialization.deserialize((byte[]) input.readObject(), clz);

    response.setRequestId(requestId);
    if (isException) {
      response.setException((Exception) result);
    } else {
      response.setValue(result);
    }

    input.close();
    return response;
  }

  private Object decodeResponse(long requestId, byte[] content) throws Exception {
    return decodeResponse0(requestId, content, false);
  }

  private Object decodeException(long requestId, byte[] content) throws Exception {
    return decodeResponse0(requestId, content, true);
  }

  private Object[] decodeRequestParameter(ObjectInput input, String parameterDesc)
      throws IOException,
      ClassNotFoundException {
    if (parameterDesc == null || parameterDesc.equals("")) {
      return null;
    }
    Class<?>[] classTypes = ReflectUtils.forNames(parameterDesc);
    Object[] paramObjects = new Object[classTypes.length];
    for (int i = 0; i < classTypes.length; i++) {
      paramObjects[i] = serialization.deserialize((byte[]) input.readObject(), classTypes[i]);
    }
    return paramObjects;
  }


  /**
   * 大端
   */


  private static short bytesToShort(byte[] data, int pos) {
    return (short) (((data[pos + 1] & 0xFF)) + ((data[pos] & 0xFF) << 8));
  }

  private static int bytesToInt(byte[] data, int pos) {
    return ((data[pos] & 0xFF) << 24)
        + ((data[pos + 1] & 0xFF) << 16)
        + ((data[pos + 2] & 0xFF) << 8)
        + (data[pos + 3] & 0xFF);
  }

  private static long bytesToLong(byte[] data, int pos) {
    return ((data[pos] & 0xFFL) << 56)
        + ((data[pos + 1] & 0xFFL) << 48)
        + ((data[pos + 2] & 0xFFL) << 40)
        + ((data[pos + 3] & 0xFFL) << 32)
        + ((data[pos + 4] & 0xFFL) << 24)
        + ((data[pos + 5] & 0xFFL) << 16)
        + ((data[pos + 6] & 0xFFL) << 8)
        + (data[pos + 7] & 0xFFL);
  }

  private static void shortToBytes(byte[] dst, int pos, short val) {
    dst[pos + 1] = (byte) (val & 0xff);
    dst[pos + 0] = (byte) ((val >> 8) & 0xff);
  }

  private static void intToBytes(byte[] dst, int pos, int val) {
    dst[pos + 3] = (byte) (val & 0xff);
    dst[pos + 2] = (byte) ((val >> 8) & 0xff);
    dst[pos + 1] = (byte) ((val >> 16) & 0xff);
    dst[pos + 0] = (byte) ((val >> 24) & 0xff);
  }

  private static void longToBytes(byte[] dst, int pos, long val) {
    dst[pos + 7] = (byte) (val & 0xff);
    dst[pos + 6] = (byte) ((val >> 8) & 0xff);
    dst[pos + 5] = (byte) ((val >> 16) & 0xff);
    dst[pos + 4] = (byte) ((val >> 24) & 0xff);

    dst[pos + 3] = (byte) ((val >> 32) & 0xff);
    dst[pos + 2] = (byte) ((val >> 40) & 0xff);
    dst[pos + 1] = (byte) ((val >> 48) & 0xff);
    dst[pos + 0] = (byte) ((val >> 56) & 0xff);
  }

}
