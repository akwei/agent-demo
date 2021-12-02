package com.github.akwei.agentdemo.agent2;

import java.util.HashMap;
import java.util.Map;

public class Dispatcher {

    public static final Map<String, Action> ACTION_MAP = new HashMap<>();

    public static void putAction(String key, Action action) {
        ACTION_MAP.put(key, action);
    }

    public static Action getAction(String key) {
        return ACTION_MAP.get(key);
    }

}

