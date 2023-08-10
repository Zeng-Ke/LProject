package com.example.lproject.base

import androidx.lifecycle.LifecycleOwner

interface IUiView : LifecycleOwner {

    fun showLoading()

    fun dismissLoading()

    fun  onException(t:Throwable)
}