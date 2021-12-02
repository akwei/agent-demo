package com.github.akwei.agentdemo.agent2;

import java.sql.Connection;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class JdbcConCreateStatementAction implements Action {

    private final ForwardLock lock = new ForwardLock();

    @Override
    public ForwardLock.Release<Map<Object, Object>> enter(Object obj, String method, Object[] args) {
        return lock.acquire(() -> {
            return (Map<Object, Object>) new HashMap<>();
        });
    }

    // Statement stm = con.createStatement();
    @Override
    public void exit(ForwardLock.Release<Map<Object, Object>> release, Object obj, String method,
                     Object[] args, Object retValue, Throwable throwable) {
        release.apply(map -> {
            // method = "con.createStatement";
            Connection con = (Connection) obj;
            Statement stm = (Statement) retValue;
            ((AgentFieldAccessor) stm).setValue(con);
        });
    }

    private void info(String data) {
        System.out.println(data);
    }
}

