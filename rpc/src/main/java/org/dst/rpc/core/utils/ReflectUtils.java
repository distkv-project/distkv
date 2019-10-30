package org.dst.rpc.core.utils;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;


public class ReflectUtils {

  private static final ConcurrentMap<String, Class<?>> nameToClassCache = new ConcurrentHashMap<>();
  private static final ConcurrentMap<String, Method> nameToMethodCache = new ConcurrentHashMap<>();

  private static final String EMPTY_PARAM = "void";
  private static final String PARAM_CLASS_SPLIT = ",";
  private static final Class<?>[] EMPTY_CLASS_ARRAY = new Class<?>[0];

  static {
    nameToClassCache.put("boolean", boolean.class);
    nameToClassCache.put("byte", byte.class);
    nameToClassCache.put("char", char.class);
    nameToClassCache.put("short", short.class);
    nameToClassCache.put("int", int.class);
    nameToClassCache.put("long", long.class);
    nameToClassCache.put("float", float.class);
    nameToClassCache.put("double", double.class);
    nameToClassCache.put("void", void.class);
  }

  public static List<Method> parseMethod(Class clazz) {
    Method[] methods = clazz.getMethods();
    List<Method> ret = new ArrayList<>();
    for (Method method : methods) {
      boolean isPublic = Modifier.isPublic(method.getModifiers());
      boolean isNotObjectClass = method.getDeclaringClass() != Object.class;
      if (isPublic && isNotObjectClass) {
        ret.add(method);
      }
    }
    return ret;
  }

  public static Class<?> forName(String className) throws ClassNotFoundException {
    if (null == className || "".equals(className)) {
      throw new ClassNotFoundException("class name: " + className);
    }

    Class<?> clz = nameToClassCache.get(className);
    if (clz != null) {
      return clz;
    }
    clz = forNameWithoutCache(className);
    nameToClassCache.putIfAbsent(className, clz);
    return clz;
  }

  public static Class<?>[] forNames(String classList) throws ClassNotFoundException {
    if (classList == null || "".equals(classList) || EMPTY_PARAM.equals(classList)) {
      return EMPTY_CLASS_ARRAY;
    }

    String[] classNames = classList.split(PARAM_CLASS_SPLIT);
    Class<?>[] classTypes = new Class<?>[classNames.length];
    for (int i = 0; i < classNames.length; i++) {
      String className = classNames[i];
      classTypes[i] = forName(className);
    }
    return classTypes;
  }

  public static String getMethodDesc(Method method) {
    StringBuilder sb = new StringBuilder();
    sb.append(method.getName()).append("(");
    if (method.getParameterCount() > 0) {
      for (Class c : method.getParameterTypes()) {
        sb.append(c.getName()).append(",");
      }
      sb.setLength(sb.length() - ",".length());
    }
    sb.append(")");
    return sb.toString();
  }

  public static String getMethodDesc(String methodName, String paramDesc) {
    if (paramDesc == null || "".equals(paramDesc)) {
      return methodName + "()";
    } else {
      return methodName + "(" + paramDesc + ")";
    }
  }

  private static Class<?> forNameWithoutCache(String className) throws ClassNotFoundException {
    if (!className.endsWith("[]")) {
      return Class.forName(className, true, Thread.currentThread().getContextClassLoader());
    }

    int dimensionSiz = 0;
    while (className.endsWith("[]")) {
      dimensionSiz++;
      className = className.substring(0, className.length() - 2);
    }
    int[] dimensions = new int[dimensionSiz];
    Class<?> clz = Class.forName(className, true, Thread.currentThread().getContextClassLoader());
    return Array.newInstance(clz, dimensions).getClass();
  }
}
