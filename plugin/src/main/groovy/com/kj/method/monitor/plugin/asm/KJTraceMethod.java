package com.kj.method.monitor.plugin.asm;

import org.objectweb.asm.Opcodes;

public class KJTraceMethod {
    int id;
    int accessFlag;
    String methodName;
    String className;
    String desc;

    public static KJTraceMethod create(int id, int accessFlag, String className, String methodName, String desc) {
        KJTraceMethod traceMethod = new KJTraceMethod();
        traceMethod.id = id;
        traceMethod.accessFlag = accessFlag;
        traceMethod.className = className;
        traceMethod.methodName = methodName;
        traceMethod.desc = desc;
        return traceMethod;
    }

    public boolean isNativeMethod() {
        return (accessFlag & Opcodes.ACC_NATIVE) != 0;
    }

    public String getMethodNameText() {
        if (desc == null || isNativeMethod()) {
            return this.className + "，" + this.methodName;
        } else {
            return this.className + "，" + this.methodName + "，" + desc;
        }
    }

    public String toString() {
        if (desc == null || isNativeMethod()) {
            return id + "," + accessFlag + "," + className + "," + methodName;
        } else {
            return id + "," + accessFlag + "," + className + "," + methodName + "," + desc;
        }
    }
}
