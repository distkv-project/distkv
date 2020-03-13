package com.distkv.server.storeserver;

import com.distkv.core.LogEntry;
import com.distkv.core.segment.LogSegment;

import java.util.Collections;
import java.util.List;

/**
 * a WAL Implement.
 * </p>
 * The interface for wal is not stable now.  it will change for upper logical.
 * TODO what other methods need to provide?
 *
 * @author meijie
 * @since 0.1.4
 */
public class Wal {
  private LogSegment segment = new LogSegment();
  private int lastCommitIndex;

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

  public List<LogEntry> commit(int commitIndex) {
    if (commitIndex > lastCommitIndex) {
      List<LogEntry> entries = segment.getLogEntries(lastCommitIndex + 1, commitIndex);
      lastCommitIndex = commitIndex;
      return entries;
    } else {
      return Collections.emptyList();
    }
  }

}
