package com.github.akwei.agentdemo.agent2;

import net.bytebuddy.asm.Advice;
import net.bytebuddy.implementation.bytecode.assign.Assigner;

import java.util.Map;

public class Agent2Advice3 {

    @Advice.OnMethodEnter
    public static ForwardLock.Release<Map<Object, Object>> enter(
            @Advice.This Object obj,
            @Advice.Origin("#m") String method,
            @Advice.AllArguments(readOnly = false, typing = Assigner.Typing.DYNAMIC) Object[] args) {
        Object[] tmpArgs = args;
        Action action = Dispatcher.getAction("Agent2Advice3-Action");
        ForwardLock.Release<Map<Object, Object>> release = action.enter(obj, method, tmpArgs);
        args = tmpArgs;
        return release;
    }

    @Advice.OnMethodExit
    public static void exit(@Advice.Enter ForwardLock.Release<Map<Object, Object>> release,
                            @Advice.This Object obj,
                            @Advice.Origin("#m") String method,
                            @Advice.AllArguments(readOnly = false, typing = Assigner.Typing.DYNAMIC) Object[] args) {
        Action action = Dispatcher.getAction("Agent2Advice3-Action");
        action.exit(release, obj, method, args, null, null);
    }
}

