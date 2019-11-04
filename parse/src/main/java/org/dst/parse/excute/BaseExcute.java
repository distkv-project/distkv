package org.dst.parse.excute;

import org.dst.parse.util.CodeUtils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public abstract  class BaseExcute {
    protected String key;
    protected Object value;
    protected String method;

    public  Object excute(){
        try {
            String  exec="this."+method+"()";
            Map map=new HashMap<>();
            map.put("this",this);
            return   CodeUtils.executeExpression(exec,map);
/*
            Method method1 = getClass().getMethod(method, null);
            return   method1.invoke(this,null);*/
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    };

    public void setKey(String key) {
        this.key = key;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}
