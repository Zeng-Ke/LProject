package com.example.lproject.lifecycle

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.blankj.utilcode.util.LogUtils

/**
 * author: ZK.
 * date:   On 2023-07-18.
 */
class ActivityLifecycleObserver :  DefaultLifecycleObserver {

    override fun onStart(owner: LifecycleOwner) {
        LogUtils.dTag("ActivityLifecycle","${owner}-onStart")
    }

    override fun onCreate(owner: LifecycleOwner) {
        LogUtils.dTag("ActivityLifecycle","${owner}-onCreate")
    }

    override fun onPause(owner: LifecycleOwner) {
        LogUtils.dTag("ActivityLifecycle","${owner}-onPause")
    }

    override fun onStop(owner: LifecycleOwner) {
        LogUtils.dTag("ActivityLifecycle","${owner}-onStop")
    }

    override fun onDestroy(owner: LifecycleOwner) {
        LogUtils.dTag("ActivityLifecycle","${owner}-onDestroy")
    }

}