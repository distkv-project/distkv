package org.dst.core;

public class StringValue extends FieldValue {

    private String value = null;

    public StringValue() {
        this(null);
    }

    // TODO(qwang): Should we pass by value?
    public StringValue(String value) {
        super(-1, ValueTypeEnum.STRING);
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
