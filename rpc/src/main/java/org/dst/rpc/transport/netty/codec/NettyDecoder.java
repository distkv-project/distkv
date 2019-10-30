package org.dst.rpc.transport.netty.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import java.nio.charset.Charset;
import java.util.List;
import org.dst.rpc.core.common.constants.CodecConstants;

/**
 * 用于处理半包粘包问题。
 */
public class NettyDecoder extends ByteToMessageDecoder {

  @Override
  protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
    // 数据比协议头小，直接返回
    if (in.readableBytes() <= CodecConstants.HEADER_SIZE) {
      return;
    }

    // 标记初始位置
    in.markReaderIndex();
    short magic = in.readShort();
    if (magic != CodecConstants.MAGIC_HEAD) {
      in.resetReaderIndex();
      String str = in.toString(Charset.defaultCharset());
      System.out.println(str);
      // throw new TransportException("NettyDecoder: magic number error: " + magic);
      return;
    }

    in.skipBytes(2);
    int contentLength = in.readInt();
    if (in.readableBytes() < contentLength + 8/* requestId 8 byte */) {
      in.resetReaderIndex();
      return;
    }

    // 全部读取 注意长度，千万不要把后面的包中的数据给读取了，否则后面的包的数据将不完整。
    in.resetReaderIndex();
    byte[] data = new byte[CodecConstants.HEADER_SIZE + contentLength];
    in.readBytes(data);
    out.add(data);
  }
}
