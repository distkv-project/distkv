package org.dst.client;

public class DSTClientMain {
    public static void main(String[] args) throws Exception {
        if (args.length != 2){
            System.out.println(
                    "Usage: " + EchoClient.class.getSimpleName() +" <host> <port>");
        }

        String host = args[0];
        int port = Integer.parseInt(args[1]);
        new EchoClient(host, port).start();
    }
}
