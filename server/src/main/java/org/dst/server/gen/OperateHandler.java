package org.dst.server.gen;

import org.antlr.v4.runtime.tree.TerminalNode;
import org.dst.core.KVStore;
import org.dst.core.KVStoreImpl;
import org.dst.server.base.DstBaseService;
import org.dst.server.service.DstSetServiceImpl;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.List;

public class OperateHandler extends OperateBaseListener {
    KVStore store;
    DstBaseService operateService;
    String key;  //not thread safe
    Method method;

    public OperateHandler(KVStore store) {
        this.store = store;
    }

    @Override
    public void enterOpreate(OperateParser.OpreateContext ctx) {
        System.out.println(ctx.getText());
        super.enterOpreate(ctx);
    }

    @Override
    public void exitOpreate(OperateParser.OpreateContext ctx) {
        super.exitOpreate(ctx);
    }

    @Override
    public void enterSet(OperateParser.SetContext ctx) {
        System.out.println(ctx.getText());
        operateService=new DstSetServiceImpl(store);
        super.enterSet(ctx);
    }

    @Override
    public void exitSet(OperateParser.SetContext ctx) {
        System.out.println("tuichuset");

        super.exitSet(ctx);
    }

    @Override
    public void enterSetoperate(OperateParser.SetoperateContext ctx) {
        System.out.println(ctx.getText());
        try {
          method  = operateService.getClass().getMethod(ctx.getText(), String.class, Object.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.enterSetoperate(ctx);
    }

    @Override
    public void exitSetoperate(OperateParser.SetoperateContext ctx) {
        System.out.println("tuichusetsetoperate");

        super.exitSetoperate(ctx);
    }

    @Override
    public void enterKey(OperateParser.KeyContext ctx) {
        key=ctx.getText();
        super.enterKey(ctx);

    }

    @Override
    public void exitKey(OperateParser.KeyContext ctx) {
        System.out.println("tuichu key");
        super.exitKey(ctx);
    }

    @Override
    public void enterValue(OperateParser.ValueContext ctx) {
        try {
            method.invoke(operateService,key,ctx.STRING());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }finally {
            method=null;
            key=null;
            operateService=null;
        }
        super.enterValue(ctx);

    }

    @Override
    public void exitValue(OperateParser.ValueContext ctx) {
        super.exitValue(ctx);
    }


}
