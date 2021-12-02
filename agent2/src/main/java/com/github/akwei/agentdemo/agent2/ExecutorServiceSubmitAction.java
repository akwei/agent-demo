package com.github.akwei.agentdemo.agent2;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class ExecutorServiceSubmitAction implements Action {
    private final ForwardLock lock = new ForwardLock();

    @Override
    public ForwardLock.Release<Map<Object, Object>> enter(
            Object obj, String method, Object[] args) {
        return lock.acquire(() -> {
            args[0] = new AgentRunnable((Runnable) args[0]);
            return new HashMap<>();
        });
    }

    @Override
    public void exit(ForwardLock.Release<Map<Object, Object>> release, Object obj, String method,
                     Object[] args, Object retValue, Throwable throwable) {
        release.apply(objectObjectMap -> {
        });
    }
}
