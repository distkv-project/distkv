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
public class FileSegment {

    private static final String SEGMENT_FILE_NAME_PREFIX = "DISTKV_SEGMENT-";
    public static final Pattern SEGMENT_FILE_NAME_PATTERN = Pattern.compile(SEGMENT_FILE_NAME_PREFIX + "(/d+)");
    private String baseFolder;
    private AtomicInteger nextId;
    private FileChannel fc;
    private ByteBuffer buffer;

    public FileSegment(String baseFolder) {
        File file = new File(baseFolder);
        Preconditions.checkArgument(file.isDirectory());
        File[] segmentFiles = file.listFiles();
        int baseId = 0;
        for (File segmentFile : segmentFiles) {
            String segmentFileName = segmentFile.getName();
            Matcher matcher = SEGMENT_FILE_NAME_PATTERN.matcher(segmentFileName);
            if (matcher.matches()) {
                baseId = Math.max(baseId, Integer.parseInt(matcher.group(1)));
            }
        }
        nextId = new AtomicInteger(baseId);
    }

    public void writeHeader() {
    }

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
