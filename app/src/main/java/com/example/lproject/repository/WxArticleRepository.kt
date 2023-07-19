package com.example.lproject.repository

import android.util.Log
import com.example.lproject.data.WxArticleBean
import com.example.lproject.net.RetrofitClient
import com.example.lproject.net.base.BaseRepository
import com.example.lproject.net.entity.ApiResponse

class WxArticleRepository : BaseRepository() {

    private val mService by lazy {
        RetrofitClient.service
    }

    suspend fun fetchWxArticleFromNet(): ApiResponse<List<WxArticleBean>> {
        return executeHttp {
            Log.d("=======0", Thread.currentThread().toString())
            mService.getWxArticle()
        }
    }

    suspend fun fetchWxArticleError(): ApiResponse<List<WxArticleBean>> {
        return executeHttp {
            mService.getWxArticleError()
        }
    }


}