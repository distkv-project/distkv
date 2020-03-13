package com.distkv.server.storeserver;

import com.distkv.core.LogEntry;
import com.distkv.core.segment.LogSegment;

/**
 * a WAL Implement.
 * </p>
 * The interface for wal is not stable now.  it will change for upper logical.
 *
 * @author meijie
 * @since 0.1.4
 */
public class Wal {
  private LogSegment segment = new LogSegment();

  // this used by follower
  public void append(LogEntry logEntry) {
    segment.appendLogEntry(logEntry);
  }

  public void append(byte[] value) {
    segment.appendValue(value);
  }

  public LogEntry get(int logIndex) {
    return segment.getLogEntry(logIndex);
  }

}
