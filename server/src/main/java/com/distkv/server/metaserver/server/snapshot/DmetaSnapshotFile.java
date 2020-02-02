package com.distkv.server.metaserver.server.snapshot;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.remoting.exception.CodecException;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class DmetaSnapshotFile {

  private static final Logger LOG = LoggerFactory.getLogger(DmetaSnapshotFile.class);

  private String path;

  public DmetaSnapshotFile(String path) {
    super();
    this.path = path;
  }

  public String getPath() {
    return this.path;
  }

  /**
   * Save value to snapshot file.
   */
  public boolean save(final Map<String, String> value) {
    try {
      String temp = JSON.toJSONString(value);
      FileUtils.writeStringToFile(new File(path), temp);
      return true;
    } catch (IOException e) {
      LOG.error("Fail to save snapshot", e);
      return false;
    }
  }

  public Map<String, String> load() throws IOException, CodecException {
    final String s = FileUtils.readFileToString(new File(path));
    JSONObject jsonObject = JSONObject.parseObject(s);
    Set<String> set = jsonObject.keySet();
    Map<String, String> map = new TreeMap<>();
    for (String key : set) {
      map.put(key,(String) jsonObject.get(key));
    }
    return map;
  }
}
