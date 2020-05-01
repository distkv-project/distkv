package com.distkv.core.segment;

import com.google.common.base.Preconditions;
import java.io.File;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A file segment for wal store.
 *
 * @author meijie
 */
public abstract class FileSegment {

  private static final String SEGMENT_FILE_NAME_PREFIX = "DISTKV_SEGMENT-";
  public static final Pattern SEGMENT_FILE_NAME_PATTERN = Pattern
      .compile(SEGMENT_FILE_NAME_PREFIX + "(\\d+)");

  private final File baseFolder;
  private final AtomicInteger nextId;
  private FileChannel fc;
  private ByteBuffer buffer;
  private int position;

  public FileSegment(String baseFolderPath) {
    this.baseFolder = new File(baseFolderPath);
    Preconditions.checkArgument(this.baseFolder.isDirectory());

    File[] segmentFiles = baseFolder.listFiles();
    int baseId = 0;
    if (segmentFiles != null) {
      for (File segmentFile : segmentFiles) {
        String segmentFileName = segmentFile.getName();
        Matcher matcher = SEGMENT_FILE_NAME_PATTERN.matcher(segmentFileName);
        if (matcher.matches()) {
          baseId = Math.max(baseId, Integer.parseInt(matcher.group(1)));
        }
      }
    }
    if (baseId == 0) {
      // create first segment file.
    }
    nextId = new AtomicInteger(baseId);
  }

  private void createSegmentFile() {

  }

  public ByteBuffer allocate() {
    return null;
  }

  /**
   * Sync the log from buffer to disk
   */
  private void sync() {

  }

  public void writeHeader() {
  }

  public abstract void createBuffer();

  private class SegmentHeader {

    // the major version of distkv, is used to determine compatibility
    private int majorVersion;
    // the minor version of distkv
    private int minorVersion;
    // the length of segment
    private int contentLength;
    private int checkSum;

    public int getMajorVersion() {
      return majorVersion;
    }

    public void setMajorVersion(int majorVersion) {
      this.majorVersion = majorVersion;
    }

    public int getMinorVersion() {
      return minorVersion;
    }

    public void setMinorVersion(int minorVersion) {
      this.minorVersion = minorVersion;
    }

    public int getContentLength() {
      return contentLength;
    }

    public void setContentLength(int contentLength) {
      this.contentLength = contentLength;
    }

    public int getCheckSum() {
      return checkSum;
    }

    public void setCheckSum(int checkSum) {
      this.checkSum = checkSum;
    }
  }
}
