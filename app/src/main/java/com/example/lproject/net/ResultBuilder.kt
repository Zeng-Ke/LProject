package com.example.lproject.net

import android.util.Log
import com.example.lproject.net.entity.ApiEmptyResponse
import com.example.lproject.net.entity.ApiErrorResponse
import com.example.lproject.net.entity.ApiFailedResponse
import com.example.lproject.net.entity.ApiResponse
import com.example.lproject.net.entity.ApiSuccessResponse
import com.example.lproject.toast


fun <T> ApiResponse<T>.parseData(listenerBuilder: ResultBuilder<T>.() -> Unit) {
    val listener = ResultBuilder<T>().also(listenerBuilder)
    when (this) {
        is ApiSuccessResponse -> listener.onSuccess(this.response)
        is ApiEmptyResponse -> listener.onDataEmpty()
        is ApiFailedResponse -> listener.onFailed(this.errorCode, this.errorMsg)
        is ApiErrorResponse -> listener.onError(this.throwable)
    }
    listener.onComplete()
}

class ResultBuilder<T> {

    var onSuccess: (data: T?) -> Unit = {}
    var onDataEmpty: () -> Unit = {}
    var onFailed: (errorCode: Int?, errorMsg: String?) -> Unit = { _, errorMsg ->
        errorMsg?.let { toast(it) }
    }
    var onError: (e: Throwable) -> Unit = { e ->
        e.message?.let { toast(it) }
    }
    var onComplete: () -> Unit = {}
}