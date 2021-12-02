package com.github.akwei.agentdemo.agent2;

import java.util.Map;
import java.util.function.Supplier;

public interface Action {

    ForwardLock.Release<Map<Object, Object>> enter(Object obj, String method, Object[] args);

    void exit(ForwardLock.Release<Map<Object, Object>> release,
              Object obj, String method, Object[] args, Object retValue, Throwable throwable);
}
