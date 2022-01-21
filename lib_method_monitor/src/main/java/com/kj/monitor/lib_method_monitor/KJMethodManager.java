package com.kj.monitor.lib_method_monitor;

import android.os.Looper;
import android.os.Trace;

public class KJMethodManager {
    private static Long sMainThreadId = Looper.getMainLooper().getThread().getId();
    public static void methodEnter(String name){
        if (Thread.currentThread().getId() == sMainThreadId){
            Trace.beginSection(name);
        }
    }
    public static void methodExit(){
        if (Thread.currentThread().getId() == sMainThreadId){
            Trace.endSection();
        }
    }
}
