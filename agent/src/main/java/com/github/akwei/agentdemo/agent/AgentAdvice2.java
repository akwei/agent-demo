package com.github.akwei.agentdemo.agent;

import net.bytebuddy.asm.Advice;

import javax.servlet.http.HttpServletRequest;

public class AgentAdvice2 {

    @Advice.OnMethodEnter
    public static void enter(@Advice.This HttpServletRequest request) {
        System.out.println("AgentAdvice method enter " + request);
    }

    @Advice.OnMethodExit
    public static void exit(@Advice.This HttpServletRequest request) {
        System.out.println("AgentAdvice method exit " + request);
    }
}
