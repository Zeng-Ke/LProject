package com.example.buildplugin

import org.gradle.kotlin.dsl.DependencyHandlerScope

/**
 * author: ZK.
 * date:   On 2023-07-20.
 */
object Deps {

    const val androidx = "androidx.appcompat:appcompat:1.4.1"
    const val androidxCoreKtx = "androidx.core:core-ktx:1.9.0"
    const val material = "com.google.android.material:material:1.5.0"
    const val constraintlayout = "androidx.constraintlayout:constraintlayout:2.1.3"
    const val databinding = "androidx.databinding:databinding-runtime:7.1.2"
    const val activityKtx = "androidx.activity:activity-ktx:1.5.1"
    const val utilcodex = "com.blankj:utilcodex:1.31.1"  //https://github.com/Blankj/AndroidUtilCode/blob/master/lib/utilcode/README-CN.md
    const val gson = "com.google.code.gson:gson:2.8.6"
    const val screenAutosize = "me.jessyan:autosize:1.2.1" //https://github.com/JessYanCoding/AndroidAutoSize


    // lifecycle
    const val lifecycleViewmodelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:2.4.0"
    const val lifecycleRuntimeKtx = "androidx.lifecycle:lifecycle-runtime-ktx:2.4.0"
    const val lifecycleProcess = "androidx.lifecycle:lifecycle-process:2.4.1"

    //http
    const val okhttp = "com.squareup.okhttp3:okhttp:4.1.0"
    const val retrofit = "com.squareup.retrofit2:retrofit:2.9.0"
    const val httpLoggingInterceptor = "com.squareup.okhttp3:logging-interceptor:4.9.0"
    const val retrofit2ConverterGson = "com.squareup.retrofit2:converter-gson:2.9.0"


}

fun DependencyHandlerScope.lifecycle() {
    "implementation"(Deps.lifecycleViewmodelKtx)
    "implementation"(Deps.lifecycleRuntimeKtx)
    "implementation"(Deps.lifecycleProcess)
}

fun DependencyHandlerScope.http() {
    "implementation"(Deps.okhttp)
    "implementation"(Deps.retrofit)
    "implementation"(Deps.httpLoggingInterceptor)
    "implementation"(Deps.retrofit2ConverterGson)
}


