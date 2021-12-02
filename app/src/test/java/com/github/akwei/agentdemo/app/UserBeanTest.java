package com.github.akwei.agentdemo.app;

import org.junit.Test;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class UserBeanTest {

    @Test
    public void test01() throws Exception {
        UserBean userBean = new UserBean();
        userBean.setUserId(123);
        userBean.setName("akwei");
        userBean.printInfo("abc");
//        Field agentDataField = userBean.getClass().getDeclaredField("agentData");
//        agentDataField.setAccessible(true);
//        System.out.println(agentDataField.get(userBean));
    }

    @Test
    public void test02() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        Future<?> future = executorService.submit(() -> System.out.println("user app run"));
        TimeUnit.SECONDS.sleep(5);
    }

    public void test03(Connection con) throws SQLException {
        Statement statement = con.createStatement();
        statement.execute("select * from user");
    }
}
