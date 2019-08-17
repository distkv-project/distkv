package org.dst.client;

import org.dst.client.exception.DstException;

public interface DstStringProxy {
    void put(String key, String value);

    String get(String key) throws DstException;
}
