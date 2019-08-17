package org.dst.client;

public class DefaultDstClient implements DstClient {

    @Override
    public boolean connect() {
        return true;
    }

    @Override
    public boolean isConnected() {
        return true;
    }

    @Override
    public boolean disconnect() {
        return true;
    }

    @Override
    public DstStringProxy str() {
        return null;
    }
}
