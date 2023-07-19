package com.example.lproject.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.blankj.utilcode.util.LogUtils
import com.example.lproject.data.InfoBean
import com.example.lproject.data.WxArticleBean
import com.example.lproject.net.entity.ApiResponse
import com.example.lproject.net.entity.ApiSuccessResponse
import com.example.lproject.repository.WxArticleRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * author: ZK.
 * date:   On 2023-07-10.
 */
class MainViewModel : ViewModel() {

    private val repository by lazy { WxArticleRepository() }

    private val _uiState = MutableStateFlow<ApiResponse<List<WxArticleBean>>>(ApiResponse())
    val uiState: StateFlow<ApiResponse<List<WxArticleBean>>> = _uiState.asStateFlow()


    var data = MutableLiveData<InfoBean>()
        private set
    var count = MutableLiveData<Int>()
        private set

    var infoBean = InfoBean("zhangsan", 15)

    init {
        data.postValue(infoBean)
        count.postValue(1)
    }

    var a = true

    suspend fun request() {
        if (a) {
            _uiState.value = repository.fetchWxArticleFromNet()
            a = !a
            LogUtils.iTag("http", _uiState.value)
        } else {
            _uiState.value = ApiSuccessResponse(arrayListOf())

        }
    }

    fun plus() {
        count.value?.plus(1)
        count.postValue(count.value?.plus(1))
    }


}