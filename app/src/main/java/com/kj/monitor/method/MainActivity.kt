package com.kj.monitor.method

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
//        KJMethodManager.methodEnter("onCreate")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        testM()
        Thread.sleep(1000)
//        KJMethodManager.methodExit()
        callMethod1()
    }

    private fun callMethod1() {
        for (i in 0..100) {
            Log.d("kbjay_test", "test")
        }
    }

    fun testM() {
//        KJMethodManager.methodEnter("testM")
        Thread.sleep(2000)
//        KJMethodManager.methodExit()
    }
}