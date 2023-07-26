package com.example.lproject.base.webview

import android.graphics.Color
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebView
import com.blankj.utilcode.util.AppUtils
import com.example.lproject.BuildConfig
import com.example.lproject.R
import com.example.lproject.base.BaseApp
import java.io.File

/**
 * author: ZK.
 * date:   On 2023-07-25.
 */
object WebUtil {
    fun defaultSetting(webView: WebView) {
        WebView.setWebContentsDebuggingEnabled(BuildConfig.DEBUG)//是否开启调试
        // 白色背景
        webView.setBackgroundColor(Color.TRANSPARENT)
        webView.setBackgroundResource(R.color.white)
        // 默认支持嵌套滑动
        webView.overScrollMode = View.OVER_SCROLL_NEVER
        webView.isNestedScrollingEnabled = false
        webView.settings.apply {
            // 设置自适应屏幕，两者合用
            useWideViewPort = true
            loadWithOverviewMode = true
            // 是否支持缩放，默认为true
            setSupportZoom(false)
            // 是否使用内置的缩放控件
            builtInZoomControls = false
            // 是否显示原生的缩放控件
            displayZoomControls = false
            // 设置文本缩放 默认 100
            textZoom = 100
            // 是否保存密码
            savePassword = false
            // 是否可以访问文件
            allowFileAccess = true
            // 是否支持通过js打开新窗口
            javaScriptCanOpenWindowsAutomatically = true
            // 是否支持自动加载图片
            loadsImagesAutomatically = true//页面装载完成之后再去加载图片
            blockNetworkImage = false
            // 设置编码格式
            defaultTextEncodingName = "utf-8"
            layoutAlgorithm = WebSettings.LayoutAlgorithm.NORMAL
            // 是否启用 DOM storage API
            domStorageEnabled = true
            // 是否启用 database storage API 功能
            databaseEnabled = true
            // 配置当安全源试图从不安全源加载资源时WebView的行为
            mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW

            userAgentString = "${userAgentString}-Android-${AppUtils.getAppName()}"

            // 设置缓存模式
            cacheMode = WebSettings.LOAD_DEFAULT
            // 开启 Application Caches 功能
            setAppCacheEnabled(true)
            // 设置 Application Caches 缓存目录
            val cachePath = getWebViewCachePath()
            val cacheDir = File(cachePath)
            // 设置缓存目录
            if (!cacheDir.exists() && !cacheDir.isDirectory) {
                cacheDir.mkdirs()
            }
            setAppCachePath(cachePath)
        }


    }

    /**
     * 获取 WebView 缓存文件目录
     */
    private fun getWebViewCachePath(): String {
        return BaseApp.getInstance().filesDir.absolutePath + "/webCache"
    }


}