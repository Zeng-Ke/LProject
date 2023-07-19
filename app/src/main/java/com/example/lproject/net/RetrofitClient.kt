package com.example.lproject.net

import com.example.lproject.net.base.BaseRetrofitClient
import com.example.lproject.net.services.TService
import okhttp3.OkHttpClient

object RetrofitClient : BaseRetrofitClient() {

    val service by lazy { getService(TService::class.java, TService.BASE_URL) }

    override fun handleBuilder(builder: OkHttpClient.Builder) = Unit

}
