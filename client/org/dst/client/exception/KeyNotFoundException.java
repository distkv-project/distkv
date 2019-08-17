package org.dst.client.exception;

public class KeyNotFoundException extends DstException {

    public KeyNotFoundException(String key) {
        super(String.format("The key %s doesn't exist in the store.", key));
    }
}
