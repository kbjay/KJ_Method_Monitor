package com.kj.monitor.lib_method_monitor;

import android.os.Looper;

import java.util.HashMap;
import java.util.Map;

/**
 * 添加主线程方法耗时监控
 */
public class KJMethodDurationHook implements KJMethodHookInterface {
    private Long sMainThreadId = Looper.getMainLooper().getThread().getId();
    private Map<String, Long> methodDurationMap;
    private int limitInMs;
    private KJMethodDurationCallBack callBack;

    public KJMethodDurationHook(int limitInMs, KJMethodDurationCallBack callBack) {
        methodDurationMap = new HashMap<>();
        this.limitInMs = limitInMs;
        this.callBack = callBack;
    }

    @Override
    public void onMethodEnter(String name) {
        if (Thread.currentThread().getId() == sMainThreadId) {
            methodDurationMap.put(name, System.currentTimeMillis());
        }
    }

    @Override
    public void onMethodExit(String name) {
        if (Thread.currentThread().getId() == sMainThreadId) {
            Long startTime = methodDurationMap.get(name);
            if (startTime == null) {
                return;
            }
            methodDurationMap.remove(name);
            long duration = System.currentTimeMillis() - startTime;
            if (duration > limitInMs) {
                if (callBack != null) {
                    callBack.onMethodOverLimit(duration, name);
                }
            }
        }
    }

    public interface KJMethodDurationCallBack {
        void onMethodOverLimit(long duration, String methodName);
    }
}
