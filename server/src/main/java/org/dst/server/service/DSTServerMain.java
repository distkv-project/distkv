package org.dst.server.service;

public class DSTServerMain {
    public static void main(String[] args) throws Exception {
        if(args.length != 1){
            System.err.println(
                    "Usage: " + EchoServer.class.getSimpleName() + " <port>");
            return;
        }
        int port = Integer.parseInt(args[0]);
        new EchoServer(port).start();
    }

}
