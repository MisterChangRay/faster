package com.github.misterchangray.monitor;


import com.github.misterchangray.monitor.config.MonitorConfig;
import com.github.misterchangray.monitor.config.ProfilingConfig;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;


/**
 * @description:
 * 字节码转换
 *
 * @author: Ray.chang
 *
 * @create: 2021-12-16 17:27
 *
 **/
public class CostTransformer implements ClassFileTransformer {

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
                            ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {

        if(ProfilingFilter.isNotNeedInject(className)) {
            return classfileBuffer;
        }

        MonitorConfig monitorConfig = ProfilingConfig.getMonitorConfig();
        // 这里我们限制下，只针对目标包下进行耗时统计
        if (ProfilingFilter.isNeedInject(className)) {
            return getBytes(loader, className, classfileBuffer);
        }

        return classfileBuffer;
    }


    private byte[] getBytes(ClassLoader loader,
                            String className,
                            byte[] classFileBuffer) {
        ClassReader cr = null;
        ClassWriter cw = null;
        ClassVisitor cv = null;
        if (needComputeMaxs(loader)) {
            cr = new ClassReader(classFileBuffer);
            cw = new ClassWriter(cr, ClassWriter.COMPUTE_FRAMES);
            cv = new ProfilingClassAdapter(cw, className);
            cr.accept(cv, ClassReader.EXPAND_FRAMES);
        } else {
            cr = new ClassReader(classFileBuffer);
            cw = new ClassWriter(cr, ClassWriter.COMPUTE_FRAMES);
            cv = new ProfilingClassAdapter(cw, className);
            cr.accept(cv, ClassReader.EXPAND_FRAMES);
        }

        return cw.toByteArray();

    }

    private boolean needComputeMaxs(ClassLoader classLoader) {
        if (classLoader == null) {
            return false;
        }

        String loaderName = getClassLoaderName(classLoader);
        return loaderName.equals("org.apache.catalina.loader.WebappClassLoader")
                || loaderName.equals("org.apache.catalina.loader.ParallelWebappClassLoader")
                || loaderName.equals("org.springframework.boot.loader.LaunchedURLClassLoader")
                || loaderName.startsWith("org.apache.flink.runtime.execution.librarycache.FlinkUserCodeClassLoaders")
                ;
    }

    private String getClassLoaderName(ClassLoader classLoader) {
        if (classLoader == null) {
            return "null";
        }

        return classLoader.getClass().getName();
    }
}