package org.dst.core;

public class RowDataValue extends FieldValue {

    private byte[] value = null;

    public RowDataValue() {
        this(null);
    }

    public RowDataValue(byte[] value) {
        super(-1, ValueTypeEnum.RAW_DATA);
        this.value = value;
    }

    public byte[] getValue() {
        return value;
    }

    public void setValue(byte[] value) {
        this.value = value;
    }

}
