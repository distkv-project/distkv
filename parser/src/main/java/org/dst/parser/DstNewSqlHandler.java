package org.dst.parser;

import org.dst.parser.executor.AbstractExecutor;
import org.dst.parser.executor.DstSetExecute;
import org.dst.parser.generated.DstNewSQLBaseListener;
import org.dst.parser.generated.DstNewSQLParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DstNewSqlHandler extends DstNewSQLBaseListener {

  private static final Logger LOGGER = LoggerFactory.getLogger(DstNewSqlHandler.class);

  private AbstractExecutor executor;

  public AbstractExecutor getBaseExecute() {
    return executor;
  }

  @Override
  public void enterSetPut(DstNewSQLParser.SetPutContext ctx) {
    executor = new DstSetExecute();
    //optimize method name;
    executor.setMethod("put");
  }

  @Override
  public void enterSetGet(DstNewSQLParser.SetGetContext ctx) {
    executor = new DstSetExecute();
    //optimize method name;
    executor.setMethod("get");
  }

  @Override
  public void enterKey(DstNewSQLParser.KeyContext ctx) {
    executor.setKey(ctx.getText());
  }

  @Override
  public void enterValueArray(DstNewSQLParser.ValueArrayContext ctx) {
    executor.setValue(ctx.STRING());
  }

}
