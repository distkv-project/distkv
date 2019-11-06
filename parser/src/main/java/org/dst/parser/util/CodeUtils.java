package org.dst.parser.util;

import org.apache.commons.jexl3.JexlContext;
import org.apache.commons.jexl3.JexlEngine;
import org.apache.commons.jexl3.JexlExpression;
import org.apache.commons.jexl3.MapContext;
import org.apache.commons.jexl3.internal.Engine;

import java.util.Map;

public class CodeUtils {

  private static JexlEngine jexlEngine = new Engine();

  /**
   * convert String to java code ,then executor java code return java code result
   *
   * @param jexlExpression   code string
   * @param map  key =StringExpression's parameterName, value=parameterValue
   * @return java code result
   */
  public static Object executeExpression(String jexlExpression, Map<String, Object> map) {
    JexlExpression expression = jexlEngine.createExpression(jexlExpression);
    JexlContext context = new MapContext();
    if (!map.isEmpty()) {
      map.forEach(context::set);
    }
    return expression.evaluate(context);
  }

}
