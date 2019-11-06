package org.dst.parser;

import com.google.common.base.Preconditions;
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
    // The children of the `set_put` should be 3:
    //        `set.put    key     value_array`
    Preconditions.checkState(ctx.children.size() == 3);
    final String key = ctx.children.get(1);
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
