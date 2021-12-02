package com.github.akwei.agentdemo.agent2;

import net.bytebuddy.asm.Advice;

import java.util.Map;

public class JdbcStmAdvice {

    @Advice.OnMethodEnter
    public static ForwardLock.Release<Map<Object, Object>> enter(@Advice.This Object obj,
                                                                 @Advice.Origin("#m") String method,
                                                                 @Advice.AllArguments Object[] args) {
        Action action = Dispatcher.getAction("JdbcStmAction");
        return action.enter(obj, method, args);
    }

    @Advice.OnMethodExit
    public static void exit(
            @Advice.Enter ForwardLock.Release<Map<Object, Object>> release,
            @Advice.This Object obj,
            @Advice.Origin("#m") String method,
            @Advice.AllArguments Object[] args,
            @Advice.Thrown Throwable throwable) {
        Action action = Dispatcher.getAction("JdbcStmAction");
        action.exit(release, obj, method, args, null, throwable);
    }
}
