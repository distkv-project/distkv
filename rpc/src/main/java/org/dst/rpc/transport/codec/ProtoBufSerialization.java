package org.dst.rpc.transport.codec;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.protobuf.GeneratedMessage.Builder;
import com.google.protobuf.Message;
import java.io.IOException;
import java.lang.reflect.Method;
import org.dst.rpc.core.codec.Serialization;

/**
 * 用 fastjson 作为备用方案，Request、Response等框架内部的序列化不走pb
 * 一般只有参数和返回的value使用pb序列化。
 *
 * 如果能用pb序列化则优先使用pb序列化
 */
public class ProtoBufSerialization implements Serialization {

  @Override
  public byte[] serialize(Object object) throws IOException {
    byte[] result;
    if (object instanceof Builder) {
      object = ((Builder) object).build();
    }
    if (object instanceof Message) {
      result = ((Message) object).toByteArray();
    } else {
      // throw new IllegalArgumentException(
      // "Serialize error, object must instanceof Message or Builder");
      result = backupSerialize(object);
    }
    return result;
  }

  @Override
  @SuppressWarnings("unchecked")
  public <T> T deserialize(byte[] bytes, Class<T> clazz) throws IOException {
    Builder builder;
    try {
      Method method = clazz.getMethod("newBuilder");
      builder = (Builder) method.invoke(null, null);
    } catch (Exception e) {
      // throw new IllegalArgumentException(
      //     "Get google protobuf message builder from " + clazz.getName() + "failed", e);
      return backupDeserialize(bytes, clazz);
    }
    builder.mergeFrom(bytes);
    return (T) builder.build();
  }

  private byte[] backupSerialize(Object object) throws IOException {
    SerializeWriter out = new SerializeWriter();
    JSONSerializer serializer = new JSONSerializer(out);
    serializer.config(SerializerFeature.WriteEnumUsingToString, true);
    serializer.config(SerializerFeature.WriteClassName, true);
    serializer.write(object);
    return out.toBytes("UTF-8");
  }

  private <T> T backupDeserialize(byte[] bytes, Class<T> clazz) throws IOException {
    return JSON.parseObject(new String(bytes), clazz);
  }
}
