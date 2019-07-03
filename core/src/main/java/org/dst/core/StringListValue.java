package org.dst.core;

import java.util.ArrayList;
import java.util.Iterator;
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
      return deepCopy(this.value);
    }

    public void setValue(List<String> value) {
      this.value = value;
    }

    private List<String> deepCopy(List<String> value) {
      List<String> tempValue = new ArrayList<String>();

      Iterator it = value.iterator();

      while (it.hasNext()) {
        String temp = (String) it.next();
        tempValue.add(temp);
      }

      return tempValue;
    }

}

