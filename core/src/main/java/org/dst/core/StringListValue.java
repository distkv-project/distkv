package org.dst.core;

import java.util.List;

public class StringListValue extends FieldValue {

    private List<String> value = null;

    public StringListValue() {
      this(null);
    }

    public StringListValue(List<String> value) {
      super(-1, ValueTypeEnum.STRING_LIST);
      this.value = value;
    }

    public List<String> getValue() {
      return value;
    }

    public void setValue(List<String> value) {
      this.value = value;
    }

}

