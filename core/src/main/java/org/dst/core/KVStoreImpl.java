package org.dst.core;

import org.dst.core.operatorset.DstString;
import org.dst.core.operatorset.DstList;
import org.dst.core.operatorset.DstSet;
import org.dst.core.operatorset.DstDict;
import org.dst.core.operatorset.DstTable;
import org.dst.core.operatorImpl.DstStringImpl;
import org.dst.core.operatorImpl.DstListImpl;
import org.dst.core.operatorImpl.DstSetImpl;
import org.dst.core.operatorImpl.DstDictImpl;
import org.dst.core.operatorImpl.DstTableImpl;

public class KVStoreImpl implements KVStore {

  private DstStringImpl strs;

  private DstListImpl lists;

  private DstSetImpl sets;

  private DstDictImpl dicts;

  private DstTableImpl tables;

  public KVStoreImpl() {
    this.strs = new DstStringImpl();
    this.lists = new DstListImpl();
    this.sets = new DstSetImpl();
    this.dicts = new DstDictImpl();
    this.tables = new DstTableImpl();
  }

  @Override
  public DstString strs() {
    return strs;
  }

  @Override
  public DstList lists() {
    return lists;
  }

  @Override
  public DstSet sets() {
    return sets;
  }

  @Override
  public DstDict dicts() {
    return dicts;
  }

  @Override
  public DstTable tables() {
    return tables;
  }

}
