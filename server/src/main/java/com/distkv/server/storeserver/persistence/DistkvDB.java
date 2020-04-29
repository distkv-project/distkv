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
  Then content is the eof mark. End mark, indicating that the data is written
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


  /**
   * Create DataOutputStream.
   */
  public DataOutputStream createIO() throws FileNotFoundException {
    return new DataOutputStream(
        new BufferedOutputStream(
            new FileOutputStream(DB_ROOT_PATH, true)));
  }


  /**
   * Write Welcome words.
   */
  public void welcome(DataOutputStream stream) throws IOException {
    stream.writeBytes(WELCOME_DISTKV);
    LOGGER.info(stream.size() + "  bytes have been written. <WELCOME_DISTKV>");
  }

  /**
   * Write DB Version.
   */
  public void version(DataOutputStream stream) throws IOException {
    stream.writeBytes(DB_VERSION);
    LOGGER.info(stream.size() + " bytes have been written. <DB_VERSION>");
  }

  /**
   * Write Key-Value pairs.
   */
  public void kvPairs(DataOutputStream stream, Map<String, DistkvValue> values)
      throws IOException {
    for (Map.Entry<String, DistkvValue> m : values.entrySet()) {
      //TODO (senyer) save the expire time

      stream.writeBytes(m.getKey());
      LOGGER.info(stream.size() + " bytes have been written. <Key-Value>");
      LOGGER.info("key:" + m.getKey() + "    value:" + m.getValue());
    }
  }

  /**
   * Write the raw data length with fixed 8 bytes.
   * TODO (senyer) Saves an encoded length.
   */
  public void writeLength(DataOutputStream stream, long len) throws IOException {
    stream.writeLong(len);
    LOGGER.info(stream.size() + " bytes have been written. <Key-Value>");
  }

  /**
   * Write the type of value.
   */
  public void writeObjectType(DataOutputStream stream, int type) throws IOException {
    stream.writeByte(type);
    //TODO (senyer) Finish it.
  }

  /**
   * Write the raw data length with fixed 8 bytes.
   * TODO (senyer) Saves an encoded length.
   */
  public void writeStringObject(DataOutputStream stream, long len) throws IOException {
    //TODO (senyer) Finish it.
  }

  /**
   * Write the raw data.
   * TODO (senyer) Saves an encoded object.
   */
  public void writeValueObject(DataOutputStream stream, Object object) throws IOException {
    //TODO (senyer) Finish it.
  }

  /**
   * Write the end mark.
   */
  public void writeEOF(DataOutputStream stream) throws IOException {
    //TODO (senyer) Finish it.
  }

  /**
   * Write the total db file length.
   */
  public void writeByteSUM(DataOutputStream stream) throws IOException {
    //TODO (senyer) Finish it.
  }

  /**
   * Close DataOutputStream.
   */
  public void closeIO(DataOutputStream stream) throws IOException {
    stream.close();
  }
}
