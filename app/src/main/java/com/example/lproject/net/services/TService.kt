package com.example.lproject.net.services

import com.example.lproject.data.ArticleListBean
import com.example.lproject.data.WxArticleBean
import com.example.lproject.net.entity.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * author: ZK.
 * date:   On 2023-07-12.
 */
interface TService {

    @GET("wxarticle/chapters/json")
    suspend fun getWxArticle(): ApiResponse<List<WxArticleBean>>


    @GET("article/list/{pageNo}/json")
    suspend fun getArticleList(
        @Path("pageNo") pageNo: Int,
        @Query("page_size") pageSize: Int
    ): ApiResponse<ArticleListBean>


    @GET("abc/chapters/json")
    suspend fun getWxArticleError(): ApiResponse<List<WxArticleBean>>




    companion object {
        const val BASE_URL = "https://wanandroid.com/"
    }

}