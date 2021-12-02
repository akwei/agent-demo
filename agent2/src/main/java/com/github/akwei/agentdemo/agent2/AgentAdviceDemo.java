package com.github.akwei.agentdemo.agent2;

import net.bytebuddy.asm.Advice;

import java.util.Map;

public class AgentAdviceDemo {

    @Advice.OnMethodEnter
    public static Object enter(@Advice.This Object obj) {
        Action action = Dispatcher.getAction("Agent2Advice3-Action");
        ForwardLock.Release<Map<Object, Object>> release =
                action.enter(obj, null, null);
        return release;
    }

    @Advice.OnMethodExit
    public static void exit(@Advice.This Object obj) {
        Action action = Dispatcher.getAction("Agent2Advice3-Action");
        action.exit(null, obj, null, null,
                null, null);
    }
}

