package org.dst.core.operatormpl;
import java.util.*;

import org.dst.core.operatorset.DstString;

public class DstStringmpl implements DstString{
	HashMap<String,String> strMap;
	public DstStringmpl() {
		this.strMap=new HashMap<>();
	}
	@Override
	public void put(String  Key, String Value){
		strMap.put(Key, Value);
	}

	@Override
	public String get(String Key) {
		return strMap.get(Key);
	}

	@Override
	public boolean delet(String Key) {
		strMap.remove(Key);
		if(!strMap.containsKey(Key))
			return true;
		return false;
	}
}
