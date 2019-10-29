package org.dst.server.gen;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.dst.core.KVStoreImpl;

import java.io.IOException;

public class TestAnltr4 {
    public static void main(String[] args) {
        KVStoreImpl kvStore = new KVStoreImpl();
        ANTLRInputStream input = null;
        input = new ANTLRInputStream("set.put \"k1\" \"v1\" \"v2\" \"v3\"");
        OperateLexer lexer = new OperateLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        OperateParser parser = new OperateParser(tokens);
        OperateParser.OpreateContext opreate = parser.opreate();
        ParseTreeWalker parseTreeWalker = new ParseTreeWalker();
        parseTreeWalker.walk(new OperateHandler(kvStore),opreate);
        System.out.println(kvStore.sets().get("\"k1\""));


    }
}
