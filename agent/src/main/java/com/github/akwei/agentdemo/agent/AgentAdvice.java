package com.github.akwei.agentdemo.agent;

import net.bytebuddy.asm.Advice;
import org.apache.commons.lang3.ObjectUtils;

public class AgentAdvice {

    @Advice.OnMethodEnter
    public static void enter(@Advice.This Object obj) {
        System.out.println("AgentAdvice method enter");
    }

    @Advice.OnMethodExit
    public static void exit(@Advice.This Object obj) {
        System.out.println("AgentAdvice method exit");
    }
}

