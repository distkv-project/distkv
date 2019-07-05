package org.dst.core.operatorImpl;

import org.dst.core.exception.NotImplementException;
import org.dst.core.operatorset.DstTable;
import org.dst.core.RecordEntry;
import org.dst.core.TableSpecification;

public class DstTableImpl implements DstTable {
  @Override
  public void createTable(TableSpecification tableSpec) {
    throw new NotImplementException();
  }

  @Override
  public void append(RecordEntry recordEntry) {
    throw new NotImplementException();
  }
}
