package com.example.lproject.repository

import com.example.lproject.data.ArticleListBean
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
            mService.getWxArticle()
        }
    }

    suspend fun fetchWxArticleList(pageNo: Int, pageSize: Int): ApiResponse<ArticleListBean> {
        return executeHttp { mService.getArticleList(pageNo, pageSize) }
    }


    suspend fun fetchWxArticleError(): ApiResponse<List<WxArticleBean>> {
        return executeHttp {
            mService.getWxArticleError()
        }
    }


}