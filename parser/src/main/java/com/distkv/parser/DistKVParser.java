package com.distkv.parser;

import com.distkv.parser.generated.DistKVNewSQLLexer;
import com.distkv.parser.generated.DistKVNewSQLParser;
import com.distkv.parser.po.DistKVParsedResult;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

public class DistKVParser {

  public DistKVParsedResult parse(String command) {
    DistKVNewSqlListener dstNewSqlHandler = new DistKVNewSqlListener();
    DistKVNewSQLLexer lexer = new DistKVNewSQLLexer(CharStreams.fromString(command));

    //Add  DstErrorListener
    lexer.removeErrorListeners();
    lexer.addErrorListener(DistKVSqlErrorListener.INSTANCE);
    CommonTokenStream tokens = new CommonTokenStream(lexer);

    DistKVNewSQLParser parser = new DistKVNewSQLParser(tokens);
    parser.removeErrorListeners();
    parser.addErrorListener(DistKVSqlErrorListener.INSTANCE);

    DistKVNewSQLParser.StatementContext statement = parser.statement();
    ParseTreeWalker parseTreeWalker = new ParseTreeWalker();
    parseTreeWalker.walk(dstNewSqlHandler, statement);

    return dstNewSqlHandler.getParsedResult();
  }

}
