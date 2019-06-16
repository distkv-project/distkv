package org.dst.core;

import org.dst.core.operatorImpl.*;
import org.dst.core.operatorset.*;

public class KVStoreImpl implements KVStore {
	
    private DstStringImpl str;

    private DstListImpl list;

    private DstSetImpl set;

    private DstDictImpl dict;

    private DstTableImpl table;

    public KVStoreImpl() {
        this.str = new DstStringImpl();
        this.list = new DstListImpl();
        this.set = new DstSetImpl();
        this.dict = new DstDictImpl();
        this.table = new DstTableImpl();
    }
	
    @Override
    public DstString str() {
        return str;
    }

    @Override
    public DstList list() {
        return list;
    }

    @Override
    public DstSet set() {
        return set;
    }

    @Override
    public DstDict dict() {
        return dict;
    }

    @Override
    public DstTable table() {
        return table;
    }

}
