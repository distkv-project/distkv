package org.dst.parse;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.dst.parse.execute.BaseExecute;
import org.dst.parse.gen.DstNewSQLLexer;
import org.dst.parse.gen.DstNewSQLParser;
import org.dst.parse.po.DstRequestReslut;

public class DstParser {

  public DstRequestReslut parse(String cmd) {
    DstNewSqlHandler dstNewSqlHandler = new DstNewSqlHandler();
    DstNewSQLLexer lexer = new DstNewSQLLexer(CharStreams.fromString(cmd));
    //add  dstErrorListen
    lexer.removeErrorListeners();
    lexer.addErrorListener(DstSqlErrorListener.INSTANCE);
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    DstNewSQLParser parser = new DstNewSQLParser(tokens);
    parser.removeErrorListeners();
    parser.addErrorListener(DstSqlErrorListener.INSTANCE);
    DstNewSQLParser.StatementContext statement = parser.statement();
    ParseTreeWalker parseTreeWalker = new ParseTreeWalker();
    parseTreeWalker.walk(dstNewSqlHandler, statement);
    BaseExecute execute = dstNewSqlHandler.getBaseExecute();
    Object requset = execute.excute();
    return new DstRequestReslut(execute.getRequestType(), requset);
  }

}
