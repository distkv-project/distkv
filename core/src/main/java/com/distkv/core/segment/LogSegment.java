package com.distkv.core.segment;

import com.distkv.core.block.Block;

/**
 * A segment implement for log, which store append-only value.
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
 * |       23        |      45        |              |
 * ---------------------------------------------------
 * <p>
 * the block will be cleared when LogEntry is not needed any more.
 * the data stored be changed as blew. the block1 and the index of last
 * LogEntry at block1 be removed from blockArray and  blockValueCntArray
 * ---------------------------------------------------
 * |    block2       |    block3      |    block4    |
 * ---------------------------------------------------
 * |       45        |      56        |              |
 * ---------------------------------------------------
 *
 * @author meijie
 * @since 0.1.4
 */
public class LogSegment extends AbstractNonFixedSegment {

  private static final int DEFAULT_BLOCK_SIZE = 1;

  // because the offset information of LogEntry will be cleared with
  // unneeded LogEntry, so the baseOffsetIndex means how many offset
  // information be cleared at offsetSegment. it helps to find the
  // which offset of one LogEntry.
  private int baseIndexOffset;
  // store the offset of the LogEntry at blocks.
  private IntSegment offsetSegment;

  // the blockValueCntArray record the absolute number of the blockArray.
  // It help to find out which Block the LogEntry in.
  private int[] blockValueCntArray;
  private int blockIndex;

  public LogSegment() {
    super(DEFAULT_BLOCK_SIZE);
    offsetSegment = new IntSegment(DEFAULT_BLOCK_SIZE);
    // first logEntry with 0 start offset.
    offsetSegment.put(0);
    blockValueCntArray = new int[DEFAULT_BLOCK_SIZE];
  }

  public void addLogEntry(byte[] logEntry) {
    Block block = writeLogEntry(logEntry);
    // set the Log Entry end offset.
    offsetSegment.put(block.getNextWriteOffset());
  }

  public void addLogEntries(byte[] logEntries, int[] offsetArray) {
    // TODO logEntries may bigger than block
    writeLogEntry(logEntries);
    // TODO write offset Array
  }

  private Block writeLogEntry(byte[] logEntry) {
    Block block = blockArray[blockIndex];
    int remaining = block.writeValue(logEntry);

    // write the remaining byte of logEntry to another block.
    if (remaining > 0) {
      blockValueCntArray[blockIndex] = size;
      blockIndex++;
      resize(blockIndex + 1);
      block = blockArray[blockIndex];
      block.writeValue(logEntry, logEntry.length - remaining);
    }

    size++;
    return block;
  }

  /**
   * Get LogEntry by index.
   *
   * @param logEntryIndex the index of LogEntry, start from zero.
   * @return the LogEntry.
   */
  public byte[] getLogEntry(int logEntryIndex) {
    int blockIndex = locateBlock(logEntryIndex);
    Block block = blockArray[blockIndex];
    int startOffset = offsetSegment.get(logEntryIndex - baseIndexOffset);
    int endOffset = offsetSegment.get(logEntryIndex - baseIndexOffset + 1);

    byte[] value;
    if (blockValueCntArray[blockIndex] == logEntryIndex
        && endOffset != block.getCapacity()
        && logEntryIndex != 0) {
      // TODO simple the code.
      byte[] part1 = block.read(startOffset, block.getCapacity() - startOffset);
      byte[] part2 = blockArray[blockIndex + 1].read(0, endOffset);
      value = new byte[part1.length + part2.length];
      System.arraycopy(part1, 0, value, 0, part1.length);
      System.arraycopy(part2, 0, value, part1.length, part2.length);
    } else {
      value = block.read(startOffset, endOffset - startOffset);
    }
    return value;
  }

  // TODO
  public void getNewerLogEntries(int index) {

  }

  // TODO
  public void clear() {

  }

}
