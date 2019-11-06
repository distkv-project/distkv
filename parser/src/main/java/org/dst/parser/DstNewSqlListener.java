package org.dst.parser;

import org.dst.parser.executor.AbstractExecutor;
import org.dst.parser.executor.DstSetExecutor;
import org.dst.parser.generated.DstNewSQLBaseListener;
import org.dst.parser.generated.DstNewSQLParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DstNewSqlListener extends DstNewSQLBaseListener {

  private static final Logger LOGGER = LoggerFactory.getLogger(DstNewSqlListener.class);

  private AbstractExecutor executor;

  public AbstractExecutor getExecutor() {
    return executor;
  }

  @Override
  public void enterSetPut(DstNewSQLParser.SetPutContext ctx) {

    executor = new DstSetExecutor();
    //optimize method name;
    executor.setMethod("put");
  }

  @Override
  public void enterSetGet(DstNewSQLParser.SetGetContext ctx) {
    executor = new DstSetExecutor();
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

  @Override
  public void enterSetDropByKey(DstNewSQLParser.SetDropByKeyContext ctx) {

  }

}
