package com.example.lproject.hybrid.data

import java.io.Serializable

/**
 * author: ZK.
 * date:   On 2023-07-24.
 */
data class JsCallNativeBean(val intention: String, val param: String?) : Serializable

data class NativeCallJsBean(val intention: String, val param: String?) : Serializable