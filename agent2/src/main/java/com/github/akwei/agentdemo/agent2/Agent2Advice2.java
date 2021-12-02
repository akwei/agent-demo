package com.github.akwei.agentdemo.agent2;

import net.bytebuddy.asm.Advice;

public class Agent2Advice2 {

    @Advice.OnMethodEnter
    public static void enter(@Advice.This Object obj) {
        Agent2Advice2Delegate.enter(obj);
    }

    @Advice.OnMethodExit
    public static void exit(@Advice.This Object obj) {
        Agent2Advice2Delegate.exit(obj);
    }
}
