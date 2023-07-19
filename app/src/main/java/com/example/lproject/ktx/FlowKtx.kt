package com.example.lproject.ktx

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
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
fun IUiView.launchWithLoading(requestBlock: suspend () -> Unit) {
    lifecycleScope.launch {
        flow {
            emit(requestBlock())
        }.flowOn(Dispatchers.IO)
            .onStart {
                showLoading()
            }.onCompletion {
                dismissLoading()
            }.collect()
    }
}


fun <T> Flow<ApiResponse<T>>.collectIn(
    lifecycleOwner: LifecycleOwner,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    listenerBuilder: ResultBuilder<T>.() -> Unit,
): Job = lifecycleOwner.lifecycleScope.launch {
    flowWithLifecycle(lifecycleOwner.lifecycle,
        minActiveState).collect { apiResponse: ApiResponse<T> ->
        apiResponse.parseData(listenerBuilder)
    }
}