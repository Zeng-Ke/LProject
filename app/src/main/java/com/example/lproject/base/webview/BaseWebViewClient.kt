package com.example.lproject.base.webview

import android.content.Intent
import android.graphics.Bitmap
import android.net.http.SslError
import android.os.Build
import android.webkit.SslErrorHandler
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient
import com.blankj.utilcode.util.ActivityUtils
import com.github.lzyzsd.jsbridge.BridgeWebView
import com.github.lzyzsd.jsbridge.BridgeWebViewClient

/**
 * author: ZK.
 * date:   On 2023-07-25.
 */
class BaseWebViewClient(val bridgeWebView: BridgeWebView) : BridgeWebViewClient(bridgeWebView) {

    companion object {
        private const val EMPTY_PAGE = "about:blank"
    }

    private var mWebViewClientCallBack: WebViewClientCallBack?=null

    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
        super.onPageStarted(view, url, favicon)
        bridgeWebView.settings.setBlockNetworkImage(true) //阻塞图片，让图片不显示
    }

    override fun onPageFinished(view: WebView, url: String?) {
        super.onPageFinished(view, url)
        bridgeWebView.settings.setBlockNetworkImage(false)//页面加载好以后，再放开图片
        var title = view.title
        if (url.equals(EMPTY_PAGE)) title = "网页无法打开"
        title?.let { mWebViewClientCallBack?.onReceiveTitle(it) }
    }


    override fun shouldInterceptRequest(view: WebView?, request: WebResourceRequest?): WebResourceResponse? {
        return super.shouldInterceptRequest(view, request)

    }


    override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
        if (request.url.path?.endsWith(".apk") ?: false) {
            try {
                val intent = Intent()
                intent.setAction("android.intent.action.VIEW")
                intent.setData(request.url)
                ActivityUtils.startActivity(intent)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return true
        }
        return super.shouldOverrideUrlLoading(view, request)

    }



    override fun onReceivedSslError(view: WebView?, handler: SslErrorHandler?, p2: SslError?) {
        handler?.proceed() // 接受所有网站的证书 ,但 App如果上架GooglePlay会被警告
    }

    //在API23之前只调用这个，网页内引用其他资源加载错误，比如图片、css不可用 js执行错误不会调用此方法。
    override fun onReceivedError(
        webView: WebView,
        errorCode: Int,
        description: String,
        failingUrl: String
    ) {
        super.onReceivedError(webView, errorCode, description, failingUrl)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return
        }
        val errorMsg = when (errorCode) {
            WebViewClient.ERROR_CONNECT, WebViewClient.ERROR_HOST_LOOKUP, WebViewClient.ERROR_TIMEOUT -> "网络连接错误，无法打开网页"
            else -> "无法打开网页，因为:\n$description"
        }
        webView.loadUrl(EMPTY_PAGE)  //加载空白页，避免出现默认出错页暴露网址
    }

    //API23之后，所有报错都会调用该方法。如网页中的任意一个资源获取不到（比如字体），网页就很可能显示自定义的错误界面
    override fun onReceivedError(
        view: WebView,
        request: WebResourceRequest,
        error: WebResourceError
    ) {
        super.onReceivedError(view, request, error)
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return
        }
        if (request.isForMainFrame) {//出错是网页的主页面的而不是网页中某个请求
            view.loadUrl(EMPTY_PAGE)  //加载空白页，避免出现默认出错页暴露网址
            mWebViewClientCallBack?.onReceiveError(error.errorCode, error.description.toString(), request.url.toString())
        }

    }


    fun setWebChromeClientCallBack(callBack: WebViewClientCallBack) {
        mWebViewClientCallBack = callBack
    }

    interface  WebViewClientCallBack{
        fun  onReceiveError(errorCode: Int, errorMsg: String, url: String)
        fun onReceiveTitle(title: String?)
    }

}