package com.example.lproject.net.base

import com.blankj.utilcode.util.LogUtils
import com.example.lproject.BuildConfig
import com.example.lproject.net.entity.ApiEmptyResponse
import com.example.lproject.net.entity.ApiErrorResponse
import com.example.lproject.net.entity.ApiFailedResponse
import com.example.lproject.net.entity.ApiResponse
import com.example.lproject.net.entity.ApiSuccessResponse

open class BaseRepository {

    suspend fun <T> executeHttp(block: suspend () -> ApiResponse<T>): ApiResponse<T> {
        //for test
        runCatching {
            block.invoke()
        }.onSuccess { data: ApiResponse<T> ->
            LogUtils.iTag("http", data.toString())
            return handleHttpOk(data)
        }.onFailure { e ->
            return handleHttpError(e)
        }
        return ApiEmptyResponse()
    }

    /**
     * 非后台返回错误，捕获到的异常
     */
    private fun <T> handleHttpError(e: Throwable): ApiErrorResponse<T> {
        if (BuildConfig.DEBUG) e.printStackTrace()
//        handlingExceptions(e)
        return ApiErrorResponse(e)
    }

    /**
     * 返回200，但是还要判断isSuccess
     */
    private fun <T> handleHttpOk(data: ApiResponse<T>): ApiResponse<T> {
        return if (data.isSuccess) {
            getHttpSuccessResponse(data)
        } else {
//            handlingApiExceptions(data.errorCode, data.errorMsg)
            ApiFailedResponse(data.errorCode, data.errorMsg)
        }
    }

    /**
     * 成功和数据为空的处理
     */
    private fun <T> getHttpSuccessResponse(response: ApiResponse<T>): ApiResponse<T> {
        val data = response.data
        return if (data == null || data is List<*> && (data as List<*>).isEmpty()) {
            ApiEmptyResponse()
        } else {
            ApiSuccessResponse(data)
        }
    }

}