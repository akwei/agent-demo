package com.github.akwei.agentdemo.agent2;

import org.apache.commons.lang3.ObjectUtils;

public class Agent2Advice2Delegate {

    public static void enter(Object obj) {
        ObjectUtils.isEmpty(obj);
        System.out.println("Agent2Advice2Delegate method enter");
    }

    public static void exit(Object obj) {
        System.out.println("Agent2Advice2Delegate method exit");
    }
}
