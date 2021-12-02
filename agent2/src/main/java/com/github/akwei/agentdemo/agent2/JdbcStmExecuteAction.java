package com.github.akwei.agentdemo.agent2;

import java.util.HashMap;
import java.util.Map;

public class JdbcStmExecuteAction implements Action {

    private final ForwardLock lock = new ForwardLock();

    @Override
    public ForwardLock.Release<Map<Object, Object>> enter(Object obj, String method, Object[] args) {
        return lock.acquire(() -> {
            long beginTime = System.currentTimeMillis();
            Map<Object, Object> map = new HashMap<>();
            map.put("beginTime", beginTime);
            return map;
        });
    }

    // statement.execute(sql)
    @Override
    public void exit(ForwardLock.Release<Map<Object, Object>> release, Object obj, String method,
                     Object[] args, Object retValue, Throwable throwable) {
        release.apply(map -> {
            long beginTime = (long) map.get("beginTime");
            long endTime = System.currentTimeMillis();
            String sql = (String) args[0];
            long duration = endTime - beginTime;
            // write log
            info("execute sql [" + sql + "] duration: " + duration + "ms");
        });
    }


    private void info(String data) {
        System.out.println(data);
    }
}
