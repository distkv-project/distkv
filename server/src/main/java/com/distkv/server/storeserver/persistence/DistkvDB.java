package com.distkv.server.storeserver.persistence;

import com.distkv.core.concepts.DistkvValue;
import com.distkv.core.struct.slist.Slist;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
   * Defined file total length.
   */
  private static long TOTAL_SUM = 0;

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

  private DataOutputStream writeStream;

  private DataInputStream readStream;

  public DistkvDB() {
  }

  /**
   * Save memory data into distkv.db file.
   *
   * @param values The memory data in store engine.
   * @throws IOException IOException
   */
  public void save(Map<String, DistkvValue> values) throws IOException {
    writeStream = createWriteIO();
    writeWelcome();
    writeVersion();
    writeKVPairs(values);
    writeEOF();
    writeByteSUM();
    closeWriteIO();
  }

  /**
   * Load DB data into memory.
   *
   * @throws IOException IOException
   */
  public void load() throws IOException {
    readStream = createReadIO();
    readWelcome();
    readVersion();
    readKVPairs();
    readEOF();
    readByteSUM();
    closeReadIO();
  }

  /**
   * Create DataOutputStream.
   */
  public DataOutputStream createWriteIO() {
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
   * Create DataOutputStream.
   */
  public DataInputStream createReadIO() {
    DataInputStream dataInputStream = null;
    try {
      dataInputStream = new DataInputStream(
          new BufferedInputStream(
              new FileInputStream(DB_ROOT_PATH)));
    } catch (FileNotFoundException e) {
      LOGGER.error("Create DataInputStream failed. {1}", e);
      System.out.println("Create DataInputStream failed. " + e);
    }
    return dataInputStream;
  }

  /**
   * Write Welcome words.
   */
  public void writeWelcome() throws IOException {
    writeStream.write(WELCOME_DISTKV.getBytes());
    TOTAL_SUM += WELCOME_DISTKV.length();
    LOGGER.info(writeStream.size() + "  bytes have been written. <WELCOME_DISTKV>");
    System.out.println(writeStream.size() + "  bytes have been written. <WELCOME_DISTKV>");
  }

  /**
   * Write DB Version.
   */
  public void writeVersion() throws IOException {
    writeStream.write(DB_VERSION.getBytes());
    LOGGER.info(writeStream.size() + " bytes have been written. <DB_VERSION>");
    System.out.println(writeStream.size() + " bytes have been written. <DB_VERSION>");
  }

  /**
   * Write Key-Value pairs.
   */
  public void writeKVPairs(Map<String, DistkvValue> values)
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
      LOGGER.info(writeStream.size() + " bytes have been written. <Key-Value>");
      LOGGER.info("key:" + key + "    value:" + value);
      System.out.println(writeStream.size() + " bytes have been written. <Key-Value>");
      System.out.println("key:" + key + "    value:" + value);
    }
  }

  /**
   * Write the raw data length with fixed 8 bytes. TODO (senyer) Saves an encoded length.
   */
  public void writeLength(long len) {
    try {
      writeStream.writeLong(len);
    } catch (IOException e) {
      LOGGER.error("Write length failed. {1}", e);
    }
    TOTAL_SUM += len;
    LOGGER.info(writeStream.size() + " bytes have been written. <Key-Value>");
    System.out.println(writeStream.size() + " bytes have been written. <Key-Value>");
  }

  /**
   * Write the type of value.
   */
  public void writeObjectType(int type) throws IOException {
    writeStream.writeByte(type);
  }

  /**
   * Write the raw data length with fixed 8 bytes. TODO (senyer) Saves an encoded length.
   */
  public void writeStringObject(Object val) {
    String strVal = String.valueOf(val);
    //TODO (senyer) Improve it.
    writeLength(strVal.length());
    try {
      writeStream.writeChars(strVal);
    } catch (IOException e) {
      LOGGER.error("Write string object failed. {1}", e);
    }
  }

  /*
   * Save list object. First of all, save element number,then save every elements.
   * Elements are stored in length and content by traversing in order.
   */
  @SuppressWarnings("unchecked")
  public void writeListObject(Object val) {
    List<String> listVal = (List<String>) val;
    writeLength(listVal.size());
    listVal.forEach(this::writeStringObject);
  }

  @SuppressWarnings("unchecked")
  public void writeDictObject(Object val) {
    Map<String, String> dictVal = (Map<String, String>) val;
    writeLength(dictVal.size());
    dictVal.forEach((k, v) -> {
      writeStringObject(k);
      writeStringObject(v);
    });
  }

  @SuppressWarnings("unchecked")
  public void writeSetObject(Object val) {
    Set<String> setVal = (Set<String>) val;
    writeLength(setVal.size());
    setVal.forEach(this::writeStringObject);
  }

  @SuppressWarnings("unchecked")
  public void writeSlistObject(Object val) {
    Slist slistVal = (Slist) val;

  }

  @SuppressWarnings("unchecked")
  public void writeIntsObject(Object val) {
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
        writeStringObject(value);
        break;
      case ValueType.LIST:
        /* Save a list value */
        writeListObject(value);
        break;
      case ValueType.SET:
        /* Save a set value */
        writeSetObject(value);
        break;
      case ValueType.DICT:
        /* Save a dict value */
        writeDictObject(value);
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
   * Read Welcome words.
   */
  public void readWelcome() throws IOException {
    //TODO (senyer) Improve it.
  }

  /**
   * Read DB Version.
   */
  public void readVersion() throws IOException {
    //TODO (senyer) Improve it.
  }

  /**
   * Read Key-Value pairs.
   */
  public void readKVPairs() throws IOException {
    //TODO (senyer) Improve it.
  }

  /**
   * Read the raw data length with fixed 8 bytes.
   */
  public void readLength() throws IOException {
    //TODO (senyer) Improve it.
  }

  /**
   * Read the type of value.
   */
  public void readObjectType() throws IOException {
    //TODO (senyer) Improve it.
  }

  /**
   * Read the raw data length with fixed 8 bytes. TODO (senyer) Saves an encoded length.
   */
  public void readStringObject() throws IOException {
    //TODO (senyer) Improve it.
  }

  /**
   * Read the raw data.
   */
  public void readValueObject() throws IOException {
    //TODO (senyer) Finish it. Add

  }

  /**
   * Read the end mark.
   */
  public void readEOF() throws IOException {
    //TODO (senyer) Finish it.
  }

  /**
   * Read the total db file length.
   */
  public void readByteSUM() throws IOException {
    //TODO (senyer) Finish it.
  }


  /**
   * Close DataOutputStream.
   */
  public void closeWriteIO() throws IOException {
    System.out.println(writeStream.size());
    writeStream.close();
  }

  /**
   * Close DataIntputStream.
   */
  public void closeReadIO() throws IOException {
    readStream.close();
  }

}
