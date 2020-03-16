package com.distkv.core.segment;

import com.distkv.core.LogEntry;
import com.distkv.core.block.Block;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * A segment implement for log, which store append-only value. the value
 * can't bigger than one block.
 *
 * </p>
 * {@link LogSegment} store the value to {@link LogSegment#blockArray}.
 * if given block has no enough space to store the LogEntry,
 * the remaining byte of the {@link LogEntry} will store to next block.
 * </p>
 * When block is full, the log index of last {@link LogEntry} at this block will be
 * stored to {@link LogSegment#blockValueCntArray}.
 * ---------------------------------------------------
 * |    block1       |    block2      |    block3    |
 * ---------------------------------------------------
 * |       0         |      23        |       46     |
 * ---------------------------------------------------
 * </p>
 * the block will be cleared when {@link LogEntry} is not needed any more.
 * the data stored be changed as blew. the block1 and the index of last
 * LogEntry at block1 be removed from blockArray and  blockValueCntArray
 * ---------------------------------------------------
 * |    block2       |    block3      |    block4    |
 * ---------------------------------------------------
 * |       23        |      46        |     73       |
 * ---------------------------------------------------
 * </p>
 * the {@link LogSegment#offsetSegment} store the offset for every log entry.
 * so the user can get the log entry with given index. the {@link LogSegment}
 * use the {@link LogSegment#blockValueCntArray} to locate which block the value
 * store at and read the value by start offset and end offset for this block from
 * {@link LogSegment#offsetSegment}
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
  private int baseOffsetIndex;
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
    byte[] logEntryValue = logEntry.getValue();
    Block block = blockArray[blockIndex];
    if (block.getNextWriteOffset() == block.getCapacity()) {
      block = getNextBlock();
    }
    int remaining = block.writeValue(logEntryValue);

    // write the remaining byte of logEntry to another block.
    if (remaining > 0) {
      block = getNextBlock();
      block.writeValue(logEntryValue, logEntryValue.length - remaining);
    }

    size++;
    // set the Log Entry end offset.
    offsetSegment.put(block.getNextWriteOffset());
  }

  // TODO provide simple implement now.
  public void appendLogEntries(List<LogEntry> logEntries) {
    for (LogEntry logEntry : logEntries) {
      appendLogEntry(logEntry);
    }
  }

  private Block getNextBlock() {
    blockIndex++;
    resize(blockIndex + 1);
    blockValueCntArray[blockIndex] = size;
    return blockArray[blockIndex];
  }

  public List<LogEntry> getLogEntries(int fromIndex, int toIndex) {
    List<LogEntry> logEntries = new ArrayList<>();
    for (int i = fromIndex; i <= toIndex; i++) {
      logEntries.add(getLogEntry(i));
    }
    return logEntries;
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

    return new LogEntry.LogEntryBuilder()
        .withLogIndex(logEntryIndex)
        .withValue(value)
        .build();
  }

  public int getLogEntryStartOffset(int logIndex) {
    return offsetSegment.get(logIndex - baseOffsetIndex);
  }

  public int getLogEntryEndOffset(int logIndex) {
    return offsetSegment.get(logIndex - baseOffsetIndex + 1);
  }

  public void clear(int logIndex) {
    int blockIndex = locateBlock(logIndex);
    if (blockIndex > 0) {
      releaseBlock(blockIndex);
      this.blockIndex = blockArray.length - 1;
    }

    int relativeOffsetIndex = logIndex - baseOffsetIndex;
    int toRemoveBlockNumber = relativeOffsetIndex / offsetSegment.blockItemSize;
    offsetSegment.releaseBlock(toRemoveBlockNumber);
    baseOffsetIndex += toRemoveBlockNumber * offsetSegment.blockItemSize;
  }

}
