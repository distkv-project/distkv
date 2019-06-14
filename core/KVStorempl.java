package org.dst.core;

import java.io.IOException;
import java.util.HashMap;
import org.dst.core.operatormpl.DstStringmpl;
import org.dst.core.operatorset.DstString;

public class KVStorempl implements KVStore{
	HashMap<String,String> strMap=new HashMap<>();
	@Override
	public DstString str() {
		DstStringmpl a = new DstStringmpl(strMap);
		return a;
	}

}
