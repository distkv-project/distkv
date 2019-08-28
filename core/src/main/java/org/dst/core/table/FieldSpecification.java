package org.dst.core.table;

public class FieldSpecification {

  public final int index;

  public final String name;

  public final ValueTypeEnum valueType;

  private boolean isPrimary;

  private boolean isIndex;


  public FieldSpecification(int index, String name, ValueTypeEnum valueType,
                            boolean isPrimary, boolean shouldCreateIndex) {
    this.index = index;
    this.name = name;
    this.valueType = valueType;
    markAsPrimary(isPrimary);
    markShouldCreateIndex(shouldCreateIndex);
  }

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

  public int getIndex() {
    return index;
  }

  public String getName() {
    return name;
  }

  public ValueTypeEnum getValueType() {
    return valueType;
  }

  public void setPrimary(boolean primary) {
    isPrimary = primary;
  }

  public boolean isIndex() {
    return isIndex;
  }

  public void setIndex(boolean index) {
    isIndex = index;
  }
}
