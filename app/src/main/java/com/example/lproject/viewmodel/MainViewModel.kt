package com.example.lproject.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lproject.data.ArticleListBean
import com.example.lproject.data.InfoBean
import com.example.lproject.data.WxArticleBean
import com.example.lproject.net.entity.ApiResponse
import com.example.lproject.net.entity.ApiSuccessResponse
import com.example.lproject.repository.WxArticleRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

/**
 * author: ZK.
 * date:   On 2023-07-10.
 */
class MainViewModel : ViewModel() {

    private val repository by lazy { WxArticleRepository() }

    private val _uiState = MutableStateFlow<ApiResponse<List<WxArticleBean>>>(ApiResponse())
    val uiState: StateFlow<ApiResponse<List<WxArticleBean>>> = _uiState.asStateFlow()

    private val _articleAllListBeanState = MutableStateFlow<ApiResponse<ArticleListBean>>(ApiResponse())
     val articleAllListBeanState = _articleAllListBeanState.asStateFlow()

     val myTitleState = articleAllListBeanState.map { it.data?.datas?.get(0)?.title }.distinctUntilChanged()



    var data = MutableLiveData<InfoBean>()
        private set
    var count = MutableLiveData<Int>()
        private set

    var infoBean = InfoBean("zhangsan", 15)

    private var pageNo = 0
    private var pageSize = 20

    init {
        data.postValue(infoBean)
        count.postValue(1)
    }

    var a = true

    suspend fun request() {
        if (a) {
            _uiState.value = repository.fetchWxArticleFromNet()
            a = !a
        } else {
            _uiState.value = ApiSuccessResponse(arrayListOf())

        }
    }


    suspend fun requestArticleList(isRefresh:Boolean) {
        val a = isRefresh
        _articleAllListBeanState.value = repository.fetchWxArticleList(pageNo, pageSize)
        pageNo++
    }

    fun plus() {
        count.value?.plus(1)
        count.postValue(count.value?.plus(1))
    }


}