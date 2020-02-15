package com.distkv.parser;

import com.distkv.parser.generated.DistkvNewSQLLexer;
import com.distkv.parser.generated.DistkvNewSQLParser;
import com.distkv.parser.po.DistkvParsedResult;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

public class DistkvParser {

  public DistkvParsedResult parse(String command) {
    DistkvNewSqlListener dstNewSqlHandler = new DistkvNewSqlListener();
    DistkvNewSQLLexer lexer = new DistkvNewSQLLexer(CharStreams.fromString(command));

    //Add  DstErrorListener
    lexer.removeErrorListeners();
    lexer.addErrorListener(DistkvSqlErrorListener.INSTANCE);
    CommonTokenStream tokens = new CommonTokenStream(lexer);

    DistkvNewSQLParser parser = new DistkvNewSQLParser(tokens);
    parser.removeErrorListeners();
    parser.addErrorListener(DistkvSqlErrorListener.INSTANCE);

    DistkvNewSQLParser.StatementContext statement = parser.statement();
    ParseTreeWalker parseTreeWalker = new ParseTreeWalker();
    parseTreeWalker.walk(dstNewSqlHandler, statement);

    return dstNewSqlHandler.getParsedResult();
  }

}
