package com.github.akwei.agentdemo.agent2;

import net.bytebuddy.asm.Advice;

import javax.sql.DataSource;

public class JdbcDataSourceAdvice {

    @Advice.OnMethodEnter
    public static void enter(@Advice.This DataSource ds, @Advice.Origin("#m") String method) {
        Action action = Dispatcher.getAction("JdbcDataSource-Action");
        action.enter(ds, null, null);
    }

    @Advice.OnMethodExit
    public static void exit(@Advice.This Object obj) {
        Agent2Advice2Delegate.exit(obj);
    }
}
