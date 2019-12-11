package com.distkv.dst.asyncclient;

import com.distkv.dst.asyncclient.DstStringProxy;

/**
 * The DstAsyncClient class is used to connect to the dst server with async way.
 */
public interface DstAsyncClient {

    /**
     * Connect to dst server.
     *
     * @return True if succeeded to connect, otherwise it's false.
     */
    boolean connect();

    /**
     * Whether the client connected to dst server.
     *
     * @return True if it connected, otherwise it's false.
     */
    boolean isConnect();

    /**
     * Disconnect to dst server.
     */
    boolean disConnect();

    /**
     * Get the dst string proxy.
     *
     * @return The dst string proxy.
     */
    DstStringProxy strs();
}
