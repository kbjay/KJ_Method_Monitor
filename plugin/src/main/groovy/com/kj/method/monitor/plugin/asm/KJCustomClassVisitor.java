package com.kj.method.monitor.plugin.asm;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;

public class KJCustomClassVisitor extends ClassVisitor {

    KJCustomClassVisitor(int api) {
        super(api);
    }

    KJCustomClassVisitor(int api, ClassVisitor classVisitor) {
        super(api, classVisitor);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        MethodVisitor methodVisitor = cv.visitMethod(access, name, descriptor, signature, exceptions);
        return new KJCustomMethodVisitor(api,methodVisitor, access, name, descriptor);
    }
}

