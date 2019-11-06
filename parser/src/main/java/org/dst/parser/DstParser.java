package org.dst.parser;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.dst.parser.executor.AbstractExecutor;
import org.dst.parser.generated.DstNewSQLLexer;
import org.dst.parser.generated.DstNewSQLParser;
import org.dst.parser.po.DstParsedResult;

public class DstParser {

  public DstParsedResult parse(String command) {
    DstNewSqlHandler dstNewSqlHandler = new DstNewSqlHandler();
    DstNewSQLLexer lexer = new DstNewSQLLexer(CharStreams.fromString(command));

    //Add  DstErrorListener
    lexer.removeErrorListeners();
    lexer.addErrorListener(DstSqlErrorListener.INSTANCE);
    CommonTokenStream tokens = new CommonTokenStream(lexer);

    DstNewSQLParser parser = new DstNewSQLParser(tokens);
    parser.removeErrorListeners();
    parser.addErrorListener(DstSqlErrorListener.INSTANCE);

    DstNewSQLParser.StatementContext statement = parser.statement();
    ParseTreeWalker parseTreeWalker = new ParseTreeWalker();
    parseTreeWalker.walk(dstNewSqlHandler, statement);

    final AbstractExecutor executor = dstNewSqlHandler.getExecutor();
    Object request = executor.execute();
    return new DstParsedResult(executor.getRequestType(), request);
  }

}
