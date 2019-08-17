package org.dst.client;

import org.dst.client.exception.DstException;
import org.dst.server.service.DstStringService;

public class DstStringProxy {

    private DstStringService service;

    public DstStringProxy(DstStringService service) {
        this.service = service;
    }

    public void put(String key, String value) {
        
    }

    public String get(String key) throws DstException {

    }
}
