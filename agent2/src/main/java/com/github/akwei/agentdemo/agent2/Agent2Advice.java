package com.github.akwei.agentdemo.agent2;

import net.bytebuddy.asm.Advice;
import org.apache.commons.lang3.ObjectUtils;

public class Agent2Advice {

    @Advice.OnMethodEnter
    public static void enter(@Advice.This Object obj) {
        ObjectUtils.isEmpty(obj);
        System.out.println("Agent2Advice method enter");
    }

    @Advice.OnMethodExit
    public static void exit(@Advice.This Object obj) {
        System.out.println("Agent2Advice method exit");
    }
}
