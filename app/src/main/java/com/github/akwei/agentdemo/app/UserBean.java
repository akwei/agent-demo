package com.github.akwei.agentdemo.app;

public class UserBean {

    private long userId;
    private String name;

    public void printInfo(String data) {
        System.out.println(this.userId + " - " + this.name + " - " + data);
    }

    public void printInfo() {
        System.out.println(this.userId + " - " + this.name);
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
