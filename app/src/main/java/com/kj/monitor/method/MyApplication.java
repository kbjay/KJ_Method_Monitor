package com.kj.monitor.method;

import android.app.Application;
import android.util.Log;

import com.kj.monitor.lib_method_monitor.KJMethodDurationHook;
import com.kj.monitor.lib_method_monitor.KJMethodManager;
import com.kj.monitor.lib_method_monitor.KJSystraceHook;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initMethodHook();
    }

    private void initMethodHook() {
        KJMethodManager.addMethodHook(new KJSystraceHook());
        KJMethodManager.addMethodHook(new KJMethodDurationHook(50, new KJMethodDurationHook.KJMethodDurationCallBack() {
            @Override
            public void onMethodOverLimit(long duration, String methodName) {
                Log.w("kbjay_method", methodName + ":" + duration);
            }
        }));
    }
}
