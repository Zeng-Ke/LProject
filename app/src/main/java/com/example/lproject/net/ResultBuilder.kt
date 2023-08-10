package com.example.lproject.net

import com.example.lproject.base.IUiView
import com.example.lproject.net.entity.ApiEmptyResponse
import com.example.lproject.net.entity.ApiErrorResponse
import com.example.lproject.net.entity.ApiFailedResponse
import com.example.lproject.net.entity.ApiResponse
import com.example.lproject.net.entity.ApiSuccessResponse
import com.example.lproject.toast


fun <T> ApiResponse<T>.parseData(iUiView: IUiView,listenerBuilder: ResultBuilder<T>.() -> Unit) {
    val listener = ResultBuilder<T>(iUiView).also(listenerBuilder)
    when (this) {
        is ApiSuccessResponse -> listener.onSuccess(this.response)
        is ApiEmptyResponse -> listener.onDataEmpty()
        is ApiFailedResponse -> listener.onFailed(this.errorCode, this.errorMsg)
        is ApiErrorResponse -> listener.onError(this.throwable)
    }
    listener.onComplete()
}

open class ResultBuilder<T>(val iUiView: IUiView) {

    var onSuccess: (data: T?) -> Unit = {}
    var onDataEmpty: () -> Unit = {}
    var onFailed: (errorCode: Int?, errorMsg: String?) -> Unit = { _, errorMsg ->
        errorMsg?.let { toast(it) }
    }
    open var onError: (e: Throwable) -> Unit = { e ->
        iUiView.onException(e)
    }
    var onComplete: () -> Unit = {}
}

/*
class MResultBuilder<T>(val iUiView: IUiView) : ResultBuilder<T>() {

    override var onError: (e: Throwable) -> Unit = {
        iUiView.onException(it)
    }

}*/
