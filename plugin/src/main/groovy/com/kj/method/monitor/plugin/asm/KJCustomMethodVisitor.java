package com.kj.method.monitor.plugin.asm;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.AdviceAdapter;
import org.objectweb.asm.commons.Method;

public class KJCustomMethodVisitor extends AdviceAdapter {

    protected KJCustomMethodVisitor(int api, MethodVisitor methodVisitor, int access, String name, String descriptor) {
        super(api, methodVisitor, access, name, descriptor);
    }

    @Override
    protected void onMethodEnter() {
        super.onMethodEnter();
        System.out.println("  onMethodEnter: "+getName()+" ");
        // getName
        mv.visitLdcInsn(getName());
        // call methodEnter
        mv.visitMethodInsn(INVOKESTATIC, "com/kj/monitor/lib_method_monitor/KJMethodManager", "methodEnter", "(Ljava/lang/String;)V", false);
    }

    @Override
    protected void onMethodExit(int opcode) {
        super.onMethodExit(opcode);

        mv.visitMethodInsn(INVOKESTATIC, "com/kj/monitor/lib_method_monitor/KJMethodManager", "methodExit", "()V", false);
    }
}
