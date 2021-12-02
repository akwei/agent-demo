package com.github.akwei.agentdemo.agent;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

public class JavaAgent {
    public static void premain(String agentArgs,
                               Instrumentation instrumentation)
            throws InstantiationException {
        instrumentation.addTransformer(
                (loader, className, classBeingRedefined,
                 protectionDomain, classfileBuffer) -> {
                    // return transformed class
                    // or null if no transform is performed
                    return null;
                });
    }
}
