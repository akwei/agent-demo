package com.github.akwei.agentdemo.agent2;

import org.apache.commons.lang3.ObjectUtils;

import java.util.HashMap;
import java.util.Map;

public class Agent2Advice3Action implements Action {

    private final ForwardLock lock = new ForwardLock();

    @Override
    public ForwardLock.Release<Map<Object, Object>> enter(Object obj, String method, Object[] args) {
        return lock.acquire(() -> {
            ObjectUtils.isEmpty(obj);
            System.out.println("Agent2Advice3Action method enter");
            args[0] = "agent - " + args[0];
            return new HashMap<>();
        });
    }

    @Override
    public void exit(ForwardLock.Release<Map<Object, Object>> release,
                     Object obj, String method, Object[] args, Object retValue, Throwable throwable) {
        release.apply(map -> {
            ((AgentFieldAccessor) obj).setValue("agent value");
            System.out.println("Agent2Advice3Action method exit");
        });
    }
}

