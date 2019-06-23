package org.dst.core;

import java.util.ArrayList;

public class StringListValue extends FieldValue {

    private ArrayList<String> value = null;

    public StringListValue() {
        this(null);
    }

    public StringListValue(ArrayList<String> value) {
        super(-1, ValueTypeEnum.STRING_LIST);
        this.value = value;
    }

    public ArrayList<String> getValue() {
        return value;
    }

    public void setValue(ArrayList<String> value) {
        this.value = value;
    }
    
}
