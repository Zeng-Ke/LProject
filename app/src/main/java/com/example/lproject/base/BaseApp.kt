package com.example.lproject.base

import android.app.Application
import androidx.databinding.library.BuildConfig
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import com.blankj.utilcode.util.Utils

/**
 * author: ZK.
 * date:   On 2023-07-12.
 */
open  class BaseApp : Application() {

    companion object {
        private lateinit var instance: BaseApp

        fun getInstance() = instance
    }


    override fun onCreate() {
        super.onCreate()
        instance = this
        initTools()
        //监听App的生命周期
        ProcessLifecycleOwner.get().lifecycle.addObserver(AppLifecycleObserver())
    }

    private fun initTools() {
        Utils.init(instance)
    }


    private inner class AppLifecycleObserver : DefaultLifecycleObserver {
        override fun onStart(owner: LifecycleOwner) {
            println("App 回到前台")
        }

        override fun onDestroy(owner: LifecycleOwner) {
            println("App 退到后台了")
        }
    }

}