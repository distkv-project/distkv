package org.dst.rpc.core.codec;

import java.io.IOException;


public interface Serialization {

  byte[] serialize(Object object) throws IOException;

  <T> T deserialize(byte[] bytes, Class<T> clazz) throws IOException;

}
