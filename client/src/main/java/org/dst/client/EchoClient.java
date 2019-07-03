package org.dst.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import java.net.InetSocketAddress;

public class EchoClient {

  private final String host;

  private final int port;

  public EchoClient(String host, int port) {
    this.host = host;
    this.port = port;
  }

  public void start() throws Exception {
    EventLoopGroup group = new NioEventLoopGroup();
    Bootstrap bootstrap = new Bootstrap();
    try {
      bootstrap.group(group)
              .channel(NioSocketChannel.class)
              .remoteAddress(new InetSocketAddress(host, port))
              .handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                  socketChannel.pipeline().addLast(new EchoClientHandler());
                }
              });
      ChannelFuture future = bootstrap.connect().sync();
      future.channel().closeFuture().sync();
    } catch (InterruptedException e) {
      group.shutdownGracefully().sync();
    }
  }

}
