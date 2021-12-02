package com.github.akwei.agentdemo.agent2.loader;

import org.springframework.boot.loader.LaunchedURLClassLoader;

import java.net.URL;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class AgentClassLoader extends LaunchedURLClassLoader {
    private final Set<ClassLoader> otherClassLoaders = new CopyOnWriteArraySet<>();
    public AgentClassLoader(URL[] urls) {
        super(urls, null);
    }
    public void addClassLoader(ClassLoader classLoader) {
        if (classLoader == null || this == classLoader) {
            return;
        }
        this.otherClassLoaders.add(classLoader);
    }

    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        try {
            return super.loadClass(name, resolve);
        } catch (ClassNotFoundException e) {
            for (ClassLoader external : otherClassLoaders) {
                try {
                    Class<?> aClass = external.loadClass(name);
                    if (resolve) {
                        resolveClass(aClass);
                    }
                    return aClass;
                } catch (ClassNotFoundException ignore) {
                }
            }
            throw e;
        }
    }
}
