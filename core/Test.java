package org.dst.core;

import org.dst.core.operatormpl.DstStringmpl;

public class Test {
	public static void main(String[] args) {
		//DstStringmpl str =new DstStringmpl();
		String k1= "123";
		String v1="���RandomAccessFile.readLine()��ȡ��������";
		
		String  k2= "456@#$(";
		String v2="�����@#��%����&*��ȡ��������";
		
		String k3= "789";
		String v3="���123123123123-==��������";
		
		KVStore kv = new KVStorempl();
		kv.str().put(k1, v1);
		kv.str().put(k2, v2);
		kv.str().put(k3, v3);
		System.out.println(kv.str().get(k1));
		System.out.println(kv.str().delet(k1));
		System.out.println(kv.str().get(k2));
		System.out.println(kv.str().delet(k2));
		System.out.println(kv.str().get(k3));

	}
}
