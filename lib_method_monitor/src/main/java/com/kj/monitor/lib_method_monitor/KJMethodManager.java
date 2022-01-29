package com.kj.monitor.lib_method_monitor;

import java.util.ArrayList;

public class KJMethodManager {

    private static ArrayList<KJMethodHookInterface> methodVisitorArr = new ArrayList<>();

    /**
     * 供Asm调用，外部不可调用
     * @param name 方法名称
     */
    public static void methodEnter(String name) {
        for (int i = 0; i < methodVisitorArr.size(); i++) {
            methodVisitorArr.get(i).onMethodEnter(name);
        }
    }

    /**
     * 供Asm调用，外部不可调用
     * @param name
     */
    public static void methodExit(String name) {
        for (int i = 0; i < methodVisitorArr.size(); i++) {
            methodVisitorArr.get(i).onMethodExit(name);
        }
    }

    public static void addMethodHook(KJMethodHookInterface methodHook) {
        methodVisitorArr.add(methodHook);
    }

    public static void removeMethodHook(KJMethodHookInterface methodHook) {
        methodVisitorArr.remove(methodHook);
    }

    public static void clear() {
        methodVisitorArr.clear();
    }
}
