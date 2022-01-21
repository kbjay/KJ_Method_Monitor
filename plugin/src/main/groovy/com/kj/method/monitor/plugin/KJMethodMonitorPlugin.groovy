package com.kj.method.monitor.plugin

import com.android.build.gradle.AppExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

class KJMethodMonitorPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        print("call apply!!")

        // 注册transform
        def android = project.extensions.getByType(AppExtension)
        android.registerTransform(new KJMethodMonitorTransform())

    }
}