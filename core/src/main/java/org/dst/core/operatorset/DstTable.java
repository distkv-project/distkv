package org.dst.core.operatorset;

import org.dst.core.RecordEntry;
import org.dst.core.TableSpecification;

public interface DstTable {

  void createTable(TableSpecification tableSpec);

  void append(RecordEntry recordEntry);
}
