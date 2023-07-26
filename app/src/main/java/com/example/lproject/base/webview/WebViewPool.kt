package com.example.lproject.base.webview

import android.content.Context
import android.content.ContextWrapper
import android.content.MutableContextWrapper
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.lproject.LWebView
import com.example.lproject.base.BaseApp
import java.lang.Integer.min
import java.util.concurrent.CopyOnWriteArrayList

/**
 * author: ZK.
 * date:   On 2023-07-25.
 * desc:WebView缓存池
 */
class WebViewPool private constructor() {


    private var mIdleWebViewList: MutableList<LWebView>? = null
    private var mUsingWebViewList: MutableList<LWebView>? = null
    private val mMaxPoolSize by lazy {
        min(Runtime.getRuntime().availableProcessors(), 3)
    }


    companion object {
        val instance by lazy { WebViewPool() }

    }


    fun init() {

        mIdleWebViewList = CopyOnWriteArrayList()
        mUsingWebViewList = arrayListOf()
        val contextWrapper = MutableContextWrapper(BaseApp.getInstance())
        val webView = LWebView(contextWrapper)
        mIdleWebViewList!!.add(webView)
    }


    fun requireWebView(context: Context): LWebView {
        if (!mIdleWebViewList.isNullOrEmpty()) {
            val webView = mIdleWebViewList!!.removeAt(0)
            val contextWrapper = webView.context as MutableContextWrapper
            contextWrapper.baseContext = context
            mUsingWebViewList?.add(webView)
            return webView
        } else {
            val contextWrapper = MutableContextWrapper(BaseApp.getInstance())
            val webView = LWebView(contextWrapper)
            mUsingWebViewList?.add(webView)
            return webView
        }
    }

    fun recycleWebView(webView: LWebView) {
        val viewParent = webView.parent as? ViewGroup
        viewParent?.apply { removeView(webView) }
        webView.loadUrl("about:blank")
        if (mUsingWebViewList?.contains(webView) == true) {
            mUsingWebViewList?.remove(webView)
            if ((mIdleWebViewList?.size ?: 0) < mMaxPoolSize) {
                val context = webView.context as MutableContextWrapper
                context.baseContext = BaseApp.getInstance()
                webView.release()
                mIdleWebViewList?.add(webView)
            } else
                webView.destroy()
        } else {
            webView.destroy()
        }


    }

}