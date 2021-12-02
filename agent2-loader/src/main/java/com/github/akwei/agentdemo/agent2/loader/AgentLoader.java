package com.github.akwei.agentdemo.agent2.loader;

import com.google.common.collect.Lists;
import org.springframework.boot.loader.archive.Archive;
import org.springframework.boot.loader.archive.JarFileArchive;

import java.io.File;
import java.io.IOException;
import java.lang.instrument.Instrumentation;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.CodeSource;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class AgentLoader {
    private static final String LIB = "lib/";

    public static void premain(String agentArgs, Instrumentation instrumentation) throws Exception {
//        TimeUnit.SECONDS.sleep(5);
        JarFileArchive archive = new JarFileArchive(getArchiveFileContains());
        URL[] urls = nestArchiveUrls(archive);
        ClassLoader agentClassLoader = new AgentClassLoader(urls);
        agentClassLoader.loadClass("com.github.akwei.agentdemo.agent2.MyAgent2")
                .getDeclaredMethod("premain", String.class, Instrumentation.class)
                .invoke(null, agentArgs, instrumentation);
    }

//    public static void premain(String agentArgs, Instrumentation instrumentation) throws Exception {
////        TimeUnit.SECONDS.sleep(5);
//        JarFileArchive archive = new JarFileArchive(getArchiveFileContains());
//        URL[] urls = nestArchiveUrls(archive);
//        ClassLoader agentClassLoader = new AgentClassLoader(urls);
//        ClassLoader oldContextClassLoader = Thread.currentThread().getContextClassLoader();
//        Thread.currentThread().setContextClassLoader(agentClassLoader);
//        agentClassLoader.loadClass("com.github.akwei.agentdemo.agent2.MyAgent2")
//                .getDeclaredMethod("premain", String.class, Instrumentation.class)
//                .invoke(null, agentArgs, instrumentation)
//        ;
////        Thread.currentThread().setContextClassLoader(oldContextClassLoader);
//    }

    private static URL[] nestArchiveUrls(Archive archive) throws IOException {
        ArrayList<Archive> archives = Lists.newArrayList(
                archive.getNestedArchives(entry -> !entry.isDirectory() && entry.getName().startsWith(LIB),
                        entry -> true
                ));
        final URL[] urls = new URL[archives.size()];

        for (int i = 0; i < urls.length; i++) {
            urls[i] = archives.get(i).getUrl();
        }
        return urls;
    }

    private static File getArchiveFileContains() throws URISyntaxException {
        final ProtectionDomain protectionDomain = AgentLoader.class.getProtectionDomain();
        final CodeSource codeSource = protectionDomain.getCodeSource();
        final URI location = (codeSource == null ? null : codeSource.getLocation().toURI());
        final String path = (location == null ? null : location.getSchemeSpecificPart());
        if (path == null) {
            throw new IllegalStateException("Unable to determine code source archive");
        }
        final File root = new File(path);
        if (!root.exists() || root.isDirectory()) {
            throw new IllegalStateException("Unable to determine code source archive from " + root);
        }
        return root;
    }
}
