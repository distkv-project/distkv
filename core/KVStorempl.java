package org.dst.core;


import java.util.HashMap;
import org.dst.core.operatormpl.DstStringmpl;
import org.dst.core.operatorset.DstString;

public class KVStorempl implements KVStore{
	DstStringmpl str;
	public KVStorempl() {
		this.str=new DstStringmpl();
	}
	@Override
	public DstString str() {
		return str;
	}

}
