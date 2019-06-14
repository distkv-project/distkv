package org.dst.core;

import org.dst.core.operatorImpl.DstStringImpl;
import org.dst.core.operatorset.DstString;

public class KVStoreImpl implements KVStore {
	
    private DstStringImpl str;
	
    public KVStoreImpl() {
        this.str = new DstStringImpl();
    }
	
    @Override
    public DstString str() {
        return str;
    }

}
