package org.dst.parse;

import org.dst.parse.execute.BaseExecute;
import org.dst.parse.execute.DstSetExecute;
import org.dst.parse.gen.DstNewSQLBaseListener;
import org.dst.parse.gen.DstNewSQLParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DstNewSqlHandler extends DstNewSQLBaseListener {
  private static final Logger LOGGER = LoggerFactory.getLogger(DstNewSqlHandler.class);
  private BaseExecute baseExecute;

  public BaseExecute getBaseExecute() {
    return baseExecute;
  }

  @Override
  public void enterSet_put(DstNewSQLParser.Set_putContext ctx) {
    baseExecute = new DstSetExecute();
    //optimize method name;
    baseExecute.setMethod("put");
  }

  @Override
  public void enterSet_get(DstNewSQLParser.Set_getContext ctx) {
    LOGGER.info("enter set get.............");
    baseExecute = new DstSetExecute();
    //optimize method name;
    baseExecute.setMethod("get");
  }

  @Override
  public void enterKey(DstNewSQLParser.KeyContext ctx) {
    baseExecute.setKey(ctx.getText());
  }

  @Override
  public void enterArray_value(DstNewSQLParser.Array_valueContext ctx) {
    baseExecute.setValue(ctx.STRING());
  }

}
