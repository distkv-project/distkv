package com.distkv.server.metaserver.example.counter.snapshot;

import com.alipay.remoting.util.StringUtils;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.io.IOException;

public class CounterSnapshotFile {

  private static final Logger LOG = LoggerFactory.getLogger(CounterSnapshotFile.class);

  private String path;

  public CounterSnapshotFile(String path) {
    super();
    this.path = path;
  }

  public String getPath() {
    return this.path;
  }

  /**
   * Save value to snapshot file.
   */
  // TODO(kairbon):  here to indicates that we should use rocksdb to do snapshot later.
  public boolean save(final long value) {
    try {
      FileUtils.writeStringToFile(new File(path), String.valueOf(value));
      return true;
    } catch (IOException e) {
      LOG.error("Fail to save snapshot", e);
      return false;
    }
  }

  // TODO(kairbon):  here to indicates that we should use rocksdb to do snapshot later.
  public long load() throws IOException {
    final String s = FileUtils.readFileToString(new File(path));
    if (!StringUtils.isBlank(s)) {
      return Long.parseLong(s);
    }
    throw new IOException("Fail to load snapshot from " + path + ",content: " + s);
  }
}
