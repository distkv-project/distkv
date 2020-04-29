package com.distkv.server.storeserver.persistence;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import org.dousi.DistkvValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
  This is used to save store engine memory data into DB file.
  The Protocol:

    | WELCOME_TO_DISTKV | DB_VERSION | KET_VALUE | EOF_MARK | TOTAL_SUM |

  First content is fixed string: "WELCOME_TO_DISTKV", occupies 17 bytes.
  Then content is DB version "000x", occupies 4 bytes.
  Then content is the target key-value pair.
  Then content is the eof mark. End mark, indicating that the data is written.
  Then content is the total number of bytes.
 */
public class DistkvDB {

  private static final Logger LOGGER = LoggerFactory.getLogger(DistkvDB.class);

  /*
   * Defined file `first word`.
   */
  private static final String WELCOME_DISTKV = "WELCOME_TO_DISTKV";
  /*
   * Defined DB version.
   */
  private static final String DB_VERSION = "0001";
  /*
   * Defined DB store path.
   */
  private static final String DB_ROOT_PATH = System.getProperty("user.dir") + "/distkv.db";

  private DataOutputStream stream;

  public DistkvDB() {
    stream = createIO();
  }

  /**
   * Save memory data into distkv.db file.
   *
   * @param values The memory data in store engine.
   * @throws IOException IOException.
   */
  public void execute(Map<String, DistkvValue> values) throws IOException {
    welcome();
    version();
    kvPairs(values);
    writeEOF();
    writeByteSUM();
    closeIO();
  }

  /**
   * Create DataOutputStream.
   */
  public DataOutputStream createIO() {
    DataOutputStream dataOutputStream = null;
    try {
      dataOutputStream = new DataOutputStream(
          new BufferedOutputStream(
              new FileOutputStream(DB_ROOT_PATH, true)));
    } catch (FileNotFoundException e) {
      LOGGER.error("Create DataOutputStream failed. {1}", e);
      System.out.println("Create DataOutputStream failed. " + e);
    }
    return dataOutputStream;
  }

  /**
   * Write Welcome words.
   */
  public void welcome() throws IOException {
    stream.write(WELCOME_DISTKV.getBytes());
    LOGGER.info(stream.size() + "  bytes have been written. <WELCOME_DISTKV>");
    System.out.println(stream.size() + "  bytes have been written. <WELCOME_DISTKV>");
  }

  /**
   * Write DB Version.
   */
  public void version() throws IOException {
    stream.write(DB_VERSION.getBytes());
    LOGGER.info(stream.size() + " bytes have been written. <DB_VERSION>");
    System.out.println(stream.size() + " bytes have been written. <DB_VERSION>");
  }

  /**
   * Write Key-Value pairs.
   */
  public void kvPairs(Map<String, DistkvValue> values)
      throws IOException {
    for (Map.Entry<String, DistkvValue> m : values.entrySet()) {
      String key = m.getKey();
      DistkvValue value = m.getValue();
      //TODO (senyer) save the expire time
      //save object type.
      writeObjectType(value.getType());
      //save the key.
      writeStringObject(key);
      //save the object value.
      writeValueObject(value);
      LOGGER.info(stream.size() + " bytes have been written. <Key-Value>");
      LOGGER.info("key:" + key + "    value:" + value);
      System.out.println(stream.size() + " bytes have been written. <Key-Value>");
      System.out.println("key:" + key + "    value:" + value);
    }
  }

  /**
   * Write the raw data length with fixed 8 bytes. TODO (senyer) Saves an encoded length.
   */
  public void writeLength(long len) throws IOException {
    stream.writeLong(len);
    LOGGER.info(stream.size() + " bytes have been written. <Key-Value>");
    System.out.println(stream.size() + " bytes have been written. <Key-Value>");
  }

  /**
   * Write the type of value.
   */
  public void writeObjectType(int type) throws IOException {
    stream.writeByte(type);
  }

  /**
   * Write the raw data length with fixed 8 bytes. TODO (senyer) Saves an encoded length.
   */
  public void writeStringObject(String val) throws IOException {
    //TODO (senyer) Improve it.
    writeLength(val.length());
    stream.writeChars(val);
  }

  /**
   * Write the raw data. TODO (senyer) Saves an encoded object.
   */
  public void writeValueObject(DistkvValue val) throws IOException {
    //TODO (senyer) Finish it. Add
    int type = val.getType();
    Object value = val.getValue();

    switch (type) {
      case ValueType.STRING:
        /* Save a string value */
        writeStringObject(String.valueOf(value));
        break;
      case ValueType.LIST:
        /* Save a list value */
        break;
      case ValueType.SET:
        /* Save a set value */
        break;
      case ValueType.DICT:
        /* Save a dict value */
        break;
      case ValueType.SLIST:
        /* Save a slist value */
        break;
      case ValueType.INTS:
        /* Save a ints value */
        break;
      default:
        LOGGER.warn("Unknown value type.");
        System.err.println("Unknown value type.");
        break;
    }
  }

  /**
   * Write the end mark.
   */
  public void writeEOF() throws IOException {
    //TODO (senyer) Finish it.
  }

  /**
   * Write the total db file length.
   */
  public void writeByteSUM() throws IOException {
    //TODO (senyer) Finish it.
  }

  /**
   * Close DataOutputStream.
   */
  public void closeIO() throws IOException {
    System.out.println(stream.size());
    stream.close();
  }

}
