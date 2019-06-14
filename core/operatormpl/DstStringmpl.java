package org.dst.core.operatormpl;
import java.util.*;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;

import org.dst.core.operatorset.DstString;

public class DstStringmpl implements DstString{
	
	HashMap<String,String> strMap=new HashMap<>();
	public DstStringmpl(HashMap<String,String> strMap) {
		this.strMap=strMap;
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
