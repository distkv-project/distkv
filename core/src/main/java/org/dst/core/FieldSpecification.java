package org.dst.core;

public class FieldSpecification {

    public final int index;

    public final String name;

    public final ValueTypeEnum valueType;

    /**
     * An integer that represent some functions as bits.
     * <p>
     * bit no.              meaning
     * 0              Whether the filed is a primary key. 1 is true.
     * 1              Whether we should create an index for this field. 1 is true.
     */
    private int functionBits;

    private void markAsPrimary(boolean isPrimary) {
        // TODO(qwang):
    }

    private void markShouldCreateIndex(boolean shouldCreateIndex) {
        // TODO(qwang):
    }

    public boolean isPrimary() {
        // TODO(qwang)
        return false;
    }

    public boolean shouldCreateIndex() {
        // TODO(qwang)
        return false;
    }

    public FieldSpecification(int index, String name, ValueTypeEnum valueType,
                              boolean isPrimary, boolean shouldCreateIndex) {
        this.index = index;
        this.name = name;
        this.valueType = valueType;
        markAsPrimary(isPrimary);
        markShouldCreateIndex(shouldCreateIndex);
    }

    public static class Builder {

        private int index = -1;
        private String name = null;
        private ValueTypeEnum valueType = ValueTypeEnum.NONE;
        private boolean isPrimary = false;
        private boolean shouldCreateIndex = false;

        public Builder setIndex(int index) {
            this.index = index;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setValueType(ValueTypeEnum valueType) {
            this.valueType = valueType;
            return this;
        }

        public Builder setIsPrimary(boolean isPrimary) {
            this.isPrimary = isPrimary;
            return this;
        }

        public Builder setShouldCreateIndex(boolean shouldCreateIndex) {
            this.shouldCreateIndex = shouldCreateIndex;
            return this;
        }

        public FieldSpecification build() {
            return new FieldSpecification(index, name, valueType, isPrimary, shouldCreateIndex);
        }
    }
}
