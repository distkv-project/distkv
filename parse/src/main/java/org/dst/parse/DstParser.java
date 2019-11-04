package org.dst.parse;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.dst.parse.excute.BaseExcute;
import org.dst.parse.gen.OperateLexer;
import org.dst.parse.gen.OperateParser;

public class DstParser {


    public BaseExcute parse(String cmd){
        OperateHandler operateHandler = new OperateHandler();
        ANTLRInputStream input = new ANTLRInputStream(cmd);;
        OperateLexer lexer = new OperateLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        OperateParser parser = new OperateParser(tokens);
        OperateParser.OpreateContext opreate = parser.opreate();
        ParseTreeWalker parseTreeWalker = new ParseTreeWalker();
        parseTreeWalker.walk(operateHandler,opreate);
        return  operateHandler.getBaseExcute();
    }

}
