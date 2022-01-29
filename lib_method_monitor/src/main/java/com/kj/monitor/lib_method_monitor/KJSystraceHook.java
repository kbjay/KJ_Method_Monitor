package com.kj.monitor.lib_method_monitor;

import android.os.Looper;
import android.os.Trace;

/**
 * 给主线程method添加SystraceTag
 */
public class KJSystraceHook implements KJMethodHookInterface {
    private Long sMainThreadId = Looper.getMainLooper().getThread().getId();

    @Override
    public void onMethodEnter(String name) {
        if (Thread.currentThread().getId() == sMainThreadId) {
            Trace.beginSection(name);
        }
    }

    @Override
    public void onMethodExit(String name) {
        if (Thread.currentThread().getId() == sMainThreadId) {
            Trace.endSection();
        }
    }
}
