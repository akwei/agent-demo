package com.github.akwei.agentdemo.agent2;

import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.dynamic.ClassFileLocator;
import net.bytebuddy.dynamic.loading.ClassInjector;
import net.bytebuddy.implementation.FieldAccessor;
import net.bytebuddy.jar.asm.Opcodes;
import net.bytebuddy.pool.TypePool;

import java.io.File;
import java.io.IOException;
import java.lang.instrument.Instrumentation;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.util.Collections;

import static net.bytebuddy.matcher.ElementMatchers.*;

public class MyAgent2 {
    private static File temp;

    static {
        try {
            temp = Files.createTempDirectory("tmp").toFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    public static void premain(String agentArgs, Instrumentation instrumentation) throws Exception {
////        premain1(agentArgs, instrumentation);
//        premain2(agentArgs, instrumentation);
////        premain3(agentArgs, instrumentation);
//
//    }

//    public static void premain(String agentArgs, Instrumentation instrumentation) {
//        ClassLoader agentClassLoader = MyAgent2.class.getClassLoader();
//        new AgentBuilder.Default()
//                .with(new AgentListener())
//                .type(nameEndsWith("Bean"))
//                .transform(new AgentBuilder.Transformer.ForAdvice()
//                        .include(agentClassLoader)
//                        .advice(nameStartsWith("printInfo"),
//                                "com.github.akwei.agentdemo.agent2.Agent2Advice"))
//                .installOn(instrumentation);
//    }

//    public static void premain(String agentArgs, Instrumentation instrumentation) throws Exception {
//        injectToBootstrapClassLoader(instrumentation, "com.github.akwei.agentdemo.agent2.Agent2Advice2Delegate");
//        ClassLoader agentClassLoader = MyAgent2.class.getClassLoader();
//        new AgentBuilder.Default()
//                .with(new AgentListener())
//                .type(nameEndsWith("Bean"))
//                .transform((builder, typeDescription, classLoader, module) -> {
//                    addUserClassLoader(agentClassLoader, classLoader);
//                    return new AgentBuilder.Transformer.ForAdvice()
//                            .include(agentClassLoader)
//                            .advice(nameStartsWith("printInfo"), "com.github.akwei.agentdemo.agent2.Agent2Advice2")
//                            .transform(builder, typeDescription, classLoader, module);
//                })
//                .installOn(instrumentation);
//    }

    public static void premain(String agentArgs, Instrumentation instrumentation)
            throws Exception {
        injectToBootstrapClassLoader(instrumentation,
                "com.github.akwei.agentdemo.agent2.Dispatcher");
        injectToBootstrapClassLoader(instrumentation,
                "com.github.akwei.agentdemo.agent2.Action");
        injectToBootstrapClassLoader(instrumentation,
                "com.github.akwei.agentdemo.agent2.ForwardLock$Release");
        injectToBootstrapClassLoader(instrumentation, "com.github.akwei.agentdemo.agent2.AgentFieldAccessor");
        ClassLoader agentClassLoader = MyAgent2.class.getClassLoader();
        new AgentBuilder.Default()
                .with(new AgentListener())
//                .with(AgentBuilder.RedefinitionStrategy.RETRANSFORMATION)
//                .with(AgentBuilder.InitializationStrategy.NoOp.INSTANCE)
//                .with(AgentBuilder.TypeStrategy.Default.REBASE)
//                .with(AgentBuilder.LocationStrategy.ForClassLoader.STRONG.withFallbackTo(ClassFileLocator.ForClassLoader.ofBootLoader()))
                .ignore(isSynthetic())
                .type(nameEndsWith("Bean"))
                .transform((builder, typeDescription, classLoader, module) ->
                        builder.defineField("agentData", Object.class, Opcodes.ACC_PRIVATE)
                                .implement(AgentFieldAccessor.class)
                                .intercept(FieldAccessor.ofField("agentData")))
                .transform((builder, typeDescription, classLoader, module) -> {
                    addUserClassLoader(agentClassLoader, classLoader);
                    Action action = newInstance(
                            "com.github.akwei.agentdemo.agent2.Agent2Advice3Action",
                            agentClassLoader);
                    Dispatcher.putAction("Agent2Advice3-Action", action);
                    return new AgentBuilder.Transformer.ForAdvice()
                            .include(agentClassLoader)
                            .advice(nameStartsWith("printInfo"),
                                    "com.github.akwei.agentdemo.agent2.Agent2Advice3")
                            .transform(builder, typeDescription, classLoader, module);
                })
                .type(hasSuperType(named("java.util.concurrent.ExecutorService")))
                .transform((builder, typeDescription, classLoader, module) -> {
                    addUserClassLoader(agentClassLoader, classLoader);
                    Action action = newInstance(
                            "com.github.akwei.agentdemo.agent2.ExecutorServiceSubmitAction",
                            agentClassLoader);
                    Dispatcher.putAction("ExecutorServiceSubmitAction", action);
                    return new AgentBuilder.Transformer.ForAdvice()
                            .include(agentClassLoader)
                            .advice(nameStartsWith("submit").and(takesArgument(0, Runnable.class)),
                                    "com.github.akwei.agentdemo.agent2.ExecutorAgentAdvice")
                            .transform(builder, typeDescription, classLoader, module);
                })
                .installOn(instrumentation);
    }

    public static void premain4(String agentArgs, Instrumentation instrumentation)
            throws Exception {
        injectToBootstrapClassLoader(instrumentation,
                "com.github.akwei.agentdemo.agent2.Dispatcher");
        injectToBootstrapClassLoader(instrumentation,
                "com.github.akwei.agentdemo.agent2.Action");
        injectToBootstrapClassLoader(instrumentation,
                "com.github.akwei.agentdemo.agent2.ForwardLock$Release");
        injectToBootstrapClassLoader(instrumentation, "com.github.akwei.agentdemo.agent2.AgentFieldAccessor");
        ClassLoader agentClassLoader = MyAgent2.class.getClassLoader();
        new AgentBuilder.Default()
                .with(new AgentListener())
                .type(hasSuperType(named("java.sql.Statement")).and(not(isInterface().or(isAbstract()))))
                .transform((builder, typeDescription, classLoader, module) ->
                        builder.defineField("agentData", Object.class,
                                Opcodes.ACC_PRIVATE)
                                .implement(AgentFieldAccessor.class)
                                .intercept(
                                        FieldAccessor.ofField("agentData")))
                .transform((builder, typeDescription, classLoader, module) -> {
                    addUserClassLoader(agentClassLoader, classLoader);
                    Action action = newInstance(
                            "com.github.akwei.agentdemo.agent2.Agent2Advice3Action",
                            agentClassLoader);
                    Dispatcher.putAction("Agent2Advice3-Action", action);
                    return new AgentBuilder.Transformer.ForAdvice()
                            .include(agentClassLoader)
                            .advice(nameStartsWith("printInfo"),
                                    "com.github.akwei.agentdemo.agent2.Agent2Advice3")
                            .transform(builder, typeDescription, classLoader, module);
                })
                .installOn(instrumentation);
    }

    private static <T> T newInstance(String clsName, ClassLoader agentClassLoader) {
        try {
            return (T) agentClassLoader.loadClass(clsName).newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void injectToBootstrapClassLoader(Instrumentation instrumentation, String className) throws Exception {
        ClassFileLocator classFileLocator = ClassFileLocator.ForClassLoader.of(MyAgent2.class.getClassLoader());
        TypePool typePool = TypePool.Default.of(classFileLocator);
        TypePool.Resolution describe = typePool.describe(className);

        ClassInjector.UsingInstrumentation
                .of(temp, ClassInjector.UsingInstrumentation.Target.BOOTSTRAP, instrumentation)
                .inject(Collections.singletonMap(describe.resolve(),
                        classFileLocator.locate(className).resolve()));
    }

    static void addUserClassLoader(ClassLoader agentClassLoader, ClassLoader userClassLoader) {
        try {
            Method method = agentClassLoader.getClass().getDeclaredMethod("addClassLoader", ClassLoader.class);
            method.invoke(agentClassLoader, userClassLoader);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
