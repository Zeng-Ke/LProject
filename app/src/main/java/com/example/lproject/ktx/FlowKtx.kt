package com.yixinli.base.mvvm

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import com.blankj.utilcode.util.ThreadUtils
import com.example.lproject.base.IUiView
import com.example.lproject.net.ResultBuilder
import com.example.lproject.net.entity.ApiResponse
import com.example.lproject.net.parseData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

/**
 * author: ZK.
 * date:   On 2023-07-12.
 */
fun <T> launchFlow(
    requestBlock: suspend () -> ApiResponse<T>,
    startCallback: (() -> Unit)? = null,
    completeCallback: (() -> Unit)? = null,
): Flow<ApiResponse<T>> {
    return flow {
        emit(requestBlock())
    }.onStart {
        startCallback?.invoke()
    }.onCompletion {
        completeCallback?.invoke()
    }
}

fun IUiView.launch(requestBlock: suspend () -> Unit) {
    lifecycleScope.launch {
        var hasShowLoading = false //是否已经loading
        var hasOver = false  //是否已经加载完成
        flow {
            emit(requestBlock())
        }.flowOn(Dispatchers.IO)
            .collect()
    }
}


fun IUiView.launchWithLoading(requestBlock: suspend () -> Unit) {
    lifecycleScope.launchWhenCreated {
        var hasShowLoading = false //是否已经loading
        var hasOver = false  //是否已经加载完成
        flow {
            emit(requestBlock())
        }.flowOn(Dispatchers.IO)
            .onStart {
                ThreadUtils.runOnUiThreadDelayed({
                    if (!hasOver) {
                        hasShowLoading = true
                        showLoading()
                    }
                }, 300)
            }.onCompletion {
                hasOver = true
                if (hasShowLoading) {
                    dismissLoading()
                }
            }.collect()
    }
}

fun <T> IUiView.launchWithLoading(
    requestBlock: suspend () -> ApiResponse<T>,
    listenerBuilder: ResultBuilder<T>.() -> Unit,
) {
    lifecycleScope.launchWhenCreated {
        var hasShowLoading = false //是否已经loading
        var hasOver = false  //是否已经加载完成

        launchFlow(requestBlock, {
            ThreadUtils.runOnUiThreadDelayed({
                if (!hasOver) {
                    hasShowLoading = true
                    showLoading()
                }
            }, 300)
        }, {
            hasOver = true
            if (hasShowLoading) {
                dismissLoading()
            }
        }).collect { response ->
            response.parseData(this@launchWithLoading, listenerBuilder)
        }
    }
}


fun <T> IUiView.launchAndCollect(
    requestBlock: suspend () -> ApiResponse<T>,
    listenerBuilder: ResultBuilder<T>.() -> Unit,
) {
    lifecycleScope.launchWhenCreated {
        launchFlow(requestBlock).collect { response ->
            response.parseData(this@launchAndCollect, listenerBuilder)
        }
    }
}


/**
 * 请求带Loading&&不需要声明LiveData
 */
fun <T> IUiView.launchWithLoadingAndCollect(
    requestBlock: suspend () -> ApiResponse<T>,
    listenerBuilder: ResultBuilder<T>.() -> Unit,
) {
    lifecycleScope.launchWhenCreated {
        launchFlow(requestBlock, { showLoading() }, { dismissLoading() }).collect { response ->
            response.parseData(this@launchWithLoadingAndCollect, listenerBuilder)
        }
    }
}


fun <T> Flow<ApiResponse<T>>.collectWithBuilder(
    iUiView: IUiView,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    listenerBuilder: ResultBuilder<T>.() -> Unit,
): Job = /*iUiView.lifecycleScope.launch {
    flowWithLifecycle(
        iUiView.lifecycle,
        minActiveState
    ).collect { apiResponse: ApiResponse<T> ->
        apiResponse.parseData(iUiView, listenerBuilder)
    }}*/
    iUiView.lifecycleScope.launchWhenCreated {
        collect { apiResponse: ApiResponse<T> ->
            apiResponse.parseData(iUiView, listenerBuilder)
        }
    }


fun <T> Flow<T>.collectIn(
    iUiView: IUiView,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    action: T.() -> Unit,
): Job = iUiView.lifecycleScope.launchWhenCreated {
    /*  flowWithLifecycle(
          iUiView.lifecycle,
          minActiveState
      ).collect { t: T ->
          action.invoke(t)
      }*/
    collect { t: T ->
        action.invoke(t)
    }
}
