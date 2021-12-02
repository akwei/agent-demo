package com.github.akwei.agentdemo.agent;

import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.utility.JavaModule;

import java.lang.instrument.Instrumentation;

import static net.bytebuddy.matcher.ElementMatchers.*;

public class MyAgent {

    public static void premain(String agentArgs, Instrumentation inst) {
        String name = "com.github.akwei.agentdemo.agent.AgentAdvice";
        new AgentBuilder.Default()
                .ignore(isSynthetic())
                .or(nameStartsWith("sun."))
                .or(nameStartsWith("com.sun."))
                .or(nameStartsWith("brave."))
                .or(nameStartsWith("zipkin2."))
                .or(nameStartsWith("com.fasterxml"))
                .or(nameStartsWith("org.apache.logging"))
                .or(nameStartsWith("kotlin."))
                .or(nameStartsWith("javax."))
                .or(nameStartsWith("net.bytebuddy."))
                .or(nameStartsWith("org.junit."))
                .or(nameStartsWith("junit."))
                .or(nameStartsWith("com.intellij."))
                .type(nameEndsWith("Bean"))
                .transform(new AgentBuilder.Transformer.ForAdvice()
                        .advice(nameStartsWith("printInfo")
                                , name))
                .installOn(inst);
    }

    static class AgentListener implements AgentBuilder.Listener {
        @Override
        public void onDiscovery(String typeName, ClassLoader classLoader, JavaModule module, boolean loaded) {
        }

        @Override
        public void onTransformation(TypeDescription typeDescription, ClassLoader classLoader, JavaModule module, boolean loaded, DynamicType dynamicType) {
            System.out.println("onTransformation " + typeDescription);
        }

        @Override
        public void onIgnored(TypeDescription typeDescription, ClassLoader classLoader, JavaModule module, boolean loaded) {

        }

        @Override
        public void onError(String typeName, ClassLoader classLoader, JavaModule module, boolean loaded, Throwable throwable) {
            System.out.println("onError " + typeName + ", err:" + throwable.toString());
        }

        @Override
        public void onComplete(String typeName, ClassLoader classLoader, JavaModule module, boolean loaded) {
            System.out.println("onComplete " + typeName);
        }
    }

}
