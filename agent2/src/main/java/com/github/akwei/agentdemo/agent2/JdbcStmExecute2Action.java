package com.github.akwei.agentdemo.agent2;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class JdbcStmExecute2Action implements Action {

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
            Statement stm = (Statement) obj;
            Connection con = (Connection) ((AgentFieldAccessor) stm).getValue();
            long duration = endTime - beginTime;
            try {
                // record duration in logs
                info("execute sql [" + sql + "] duration: "
                        + duration + "ms, jdbc-url:" + con.getMetaData().getURL());
            } catch (SQLException throwables) {
            }
        });
    }

    private void info(String data) {
        System.out.println(data);
    }
}
