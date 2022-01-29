package com.kj.method.monitor.plugin.asm;

import org.apache.http.util.TextUtils;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.commons.AdviceAdapter;

public class KJCustomMethodVisitor extends AdviceAdapter {

    private String methodName;

    private static int MAX_LENGTH = 127;

    protected KJCustomMethodVisitor(int api, MethodVisitor methodVisitor, int access, String name, String descriptor, String className) {
        super(api, methodVisitor, access, name, descriptor);
        KJTraceMethod traceMethod = KJTraceMethod.create(0, access, className, name, descriptor);
        this.methodName = traceMethod.getMethodNameText();
    }

    @Override
    protected void onMethodEnter() {
        super.onMethodEnter();
        System.out.println("  onMethodEnter: " + getName() + " " + generatorMethodName());
        // getName
        mv.visitLdcInsn(generatorMethodName());
        // call methodEnter
        mv.visitMethodInsn(INVOKESTATIC, "com/kj/monitor/lib_method_monitor/KJMethodManager", "methodEnter", "(Ljava/lang/String;)V", false);
    }

    @Override
    protected void onMethodExit(int opcode) {
        super.onMethodExit(opcode);
        // getName
        mv.visitLdcInsn(generatorMethodName());
        // call methodExit
        mv.visitMethodInsn(INVOKESTATIC, "com/kj/monitor/lib_method_monitor/KJMethodManager", "methodExit", "(Ljava/lang/String;)V", false);
    }

    /**
     * 字符串太占用内存，后续考虑换成long；同时输出一张method -> long 的映射表；（暂时不考虑线上环境） todo
     *
     * @return
     */
    private String generatorMethodName() {
        String sectionName = methodName;
        int length = sectionName.length();
        if (length > MAX_LENGTH && !TextUtils.isEmpty(sectionName)) {
            // 先去掉参数
            int parmIndex = sectionName.indexOf('(');
            sectionName = sectionName.substring(0, parmIndex);
            // 如果依然更大，直接裁剪
            length = sectionName.length();
            if (length > MAX_LENGTH) {
                sectionName = sectionName.substring(length - MAX_LENGTH);
            }
        }
        return sectionName;
    }
}
