package com.kj.monitor.method

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kj.monitor.lib_method_monitor.KJMethodManager

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
//        KJMethodManager.methodEnter("onCreate")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        testM()
        Thread.sleep(1000)
//        KJMethodManager.methodExit()
    }

    fun testM() {
//        KJMethodManager.methodEnter("testM")
        Thread.sleep(2000)
//        KJMethodManager.methodExit()
    }
}