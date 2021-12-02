package com.github.akwei.agentdemo.agent2;

public class AgentRunnable implements Runnable {

    private final Runnable delegate;

    public AgentRunnable(Runnable delegate) {
        this.delegate = delegate;
    }

    @Override
    public void run() {
        this.delegate.run();
        // process task
        System.out.println("run end");
    }
}
