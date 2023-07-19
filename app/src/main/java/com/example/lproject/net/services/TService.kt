package com.example.lproject.net.services

import com.example.lproject.data.WxArticleBean
import com.example.lproject.net.entity.ApiResponse
import retrofit2.http.GET

/**
 * author: ZK.
 * date:   On 2023-07-12.
 */
interface TService {

    @GET("wxarticle/chapters/json")
    suspend fun getWxArticle(): ApiResponse<List<WxArticleBean>>

    @GET("abc/chapters/json")
    suspend fun getWxArticleError(): ApiResponse<List<WxArticleBean>>

    companion object {
        const val BASE_URL = "https://wanandroid.com/"
    }

}