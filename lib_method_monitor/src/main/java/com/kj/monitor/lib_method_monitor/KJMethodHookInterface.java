package com.kj.monitor.lib_method_monitor;

public interface KJMethodHookInterface {
    void onMethodEnter(String name);

    void onMethodExit(String name);
}
