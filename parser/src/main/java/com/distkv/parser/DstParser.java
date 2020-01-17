package com.distkv.dst.parser;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import com.distkv.dst.parser.generated.DstNewSQLLexer;
import com.distkv.dst.parser.generated.DstNewSQLParser;
import com.distkv.dst.parser.po.DstParsedResult;

public class DstParser {

  public DstParsedResult parse(String command) {
    DstNewSqlListener dstNewSqlHandler = new DstNewSqlListener();
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

    return dstNewSqlHandler.getParsedResult();
  }

}
