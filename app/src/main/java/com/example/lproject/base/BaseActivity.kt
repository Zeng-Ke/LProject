package com.example.lproject.base

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.DefaultLifecycleObserver
import com.example.lproject.lifecycle.ActivityLifecycleObserver

/**
 * author: ZK.
 * date:   On 2023-07-12.
 */
abstract  class BaseActivity : AppCompatActivity(),IUiView {


    override fun onStart() {
        super.onStart()
        lifecycle.addObserver(ActivityLifecycleObserver())
    }

    override fun showLoading() {

    }

    override fun dismissLoading() {
    }

    override fun onException(t: Throwable) {
    }




}