package com.example.lproject.ktx

import androidx.activity.ComponentActivity
import androidx.annotation.MainThread
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelLazy
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.fragment.app.Fragment

//一个全局的ViewModel
@MainThread
 inline fun <reified VM : ViewModel> ComponentActivity.applicationViewModels(
    noinline factoryProducer: (() -> ViewModelProvider.Factory)? = null
): Lazy<VM> {
    val factoryPromise = factoryProducer ?: {
        defaultViewModelProviderFactory
    }
    return ViewModelLazy(VM::class, { applicationViewModelStore }, factoryPromise)
}

val applicationViewModelStore: ViewModelStore by lazy {
    ViewModelStore()
}

@MainThread
 inline fun <reified VM : ViewModel> Fragment.applicationViewModels(
    noinline factoryProducer: (() -> ViewModelProvider.Factory)? = null
): Lazy<VM> {
    val factoryPromise = factoryProducer ?: {
        defaultViewModelProviderFactory
    }
    return ViewModelLazy(VM::class, { applicationViewModelStore }, factoryPromise)
}