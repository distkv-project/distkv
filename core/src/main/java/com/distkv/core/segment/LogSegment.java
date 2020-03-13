package com.distkv.core.segment;

import com.distkv.core.LogEntry;
import com.distkv.core.block.Block;

import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * A segment implement for log, which store append-only value. the value
 * can't bigger than one block.
 *
 * </p>
 * LogSegment store the value to block array. if given block has no
 * enough space to store the LogEntry, the remaining byte of the LogEntry
 * will store to next block.
 * </p>
 * When block is full, the log index of last LogEntry at this block will be
 * stored to blockValueCntArray.
 * ---------------------------------------------------
 * |    block1       |    block2      |    block3    |
 * ---------------------------------------------------
 * |       0         |      23        |       46     |
 * ---------------------------------------------------
 * <p>
 * the block will be cleared when LogEntry is not needed any more.
 * the data stored be changed as blew. the block1 and the index of last
 * LogEntry at block1 be removed from blockArray and  blockValueCntArray
 * ---------------------------------------------------
 * |    block2       |    block3      |    block4    |
 * ---------------------------------------------------
 * |       23        |      46        |     73       |
 * ---------------------------------------------------
 *
 * @author meijie
 * @since 0.1.4
 */
public class LogSegment extends AbstractNonFixedSegment {

  private static final int DEFAULT_BLOCK_SIZE = 1;
  // when follower request newer log entries, the MAX_BATCH_NUMBER
  // controls the max number should transfer to follower at one time.
  private static final int MAX_BATCH_NUMBER = 10;

  // because the offset information of LogEntry will be cleared with
  // unneeded LogEntry, so the baseOffsetIndex means how many offset
  // information has been cleared at offsetSegment. it helps to find out
  // which is the offset of one LogEntry.
  private int baseIndexOffset;
  // store the offset of the LogEntry at blocks.
  private IntSegment offsetSegment;

  public LogSegment() {
    super(DEFAULT_BLOCK_SIZE);
    offsetSegment = new IntSegment(DEFAULT_BLOCK_SIZE);

    // set first element start offset to block size, for start offset,
    // it means the beginning of next block. for end offset, it means
    // the ending of this block
    offsetSegment.put(pool.getBlockSize());
    blockValueCntArray = new int[DEFAULT_BLOCK_SIZE];
  }

  public void appendLogEntry(LogEntry logEntry) {
    checkArgument(logEntry.getLogIndex() == size);
    appendValue(logEntry.getValue());
  }

  // TODO provide simple implement now.
  public void appendLogEntries(List<LogEntry> logEntries) {
    for (LogEntry logEntry : logEntries) {
      appendLogEntry(logEntry);
    }
  }

  public void appendValue(byte[] logEntryValue) {
    Block block = blockArray[blockIndex];
    int remaining = block.writeValue(logEntryValue);

    // write the remaining byte of logEntry to another block.
    if (remaining > 0) {
      blockIndex++;
      resize(blockIndex + 1);
      blockValueCntArray[blockIndex] = size;
      block = blockArray[blockIndex];
      block.writeValue(logEntryValue, logEntryValue.length - remaining);
    }

    size++;
    // set the Log Entry end offset.
    offsetSegment.put(block.getNextWriteOffset());
  }

  /**
   * Get LogEntry by index.
   *
   * @param logEntryIndex the index of LogEntry, start from zero.
   * @return the LogEntry.
   */
  public LogEntry getLogEntry(int logEntryIndex) {
    int blockIndex = locateBlock(logEntryIndex);
    Block block = blockArray[blockIndex];
    int startOffset = getLogEntryStartOffset(logEntryIndex);
    int endOffset = getLogEntryEndOffset(logEntryIndex);

    byte[] value;
    if (blockValueCntArray.length > blockIndex + 1
        && blockValueCntArray[blockIndex + 1] == logEntryIndex
        && endOffset != block.getCapacity()
        && logEntryIndex != 0) {
      // this is the last log entry at given block and be stored across two blocks
      byte[] part1 = block.read(startOffset, block.getCapacity() - startOffset);
      byte[] part2 = blockArray[blockIndex + 1].read(0, endOffset);
      value = new byte[part1.length + part2.length];
      System.arraycopy(part1, 0, value, 0, part1.length);
      System.arraycopy(part2, 0, value, part1.length, part2.length);
    } else {
      if (startOffset == block.getCapacity()) {
        startOffset = 0;
      }
      value = block.read(startOffset, endOffset - startOffset);
    }
    return new LogEntry(logEntryIndex, value);
  }

  public int getLogEntryStartOffset(int logIndex) {
    return offsetSegment.get(logIndex - baseIndexOffset);
  }

  public int getLogEntryEndOffset(int logIndex) {
    return offsetSegment.get(logIndex - baseIndexOffset + 1);
  }

  // TODO
  public void clear(int logIndex) {
  }

}
