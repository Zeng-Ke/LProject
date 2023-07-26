package com.example.lproject.base.webview

import android.net.Uri
import android.webkit.ConsoleMessage
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebView
import com.blankj.utilcode.util.LogUtils
import com.example.lproject.R

class BaseWebChromeClient() : WebChromeClient() {

    private val TAG = "BaseWebChromeClient"
    private var mChooseFilesCallback: ValueCallback<Array<Uri>>? = null
    private var mWebViewCallBack: WebChromeClientCallBack? = null

    /**
     * 网页控制台输入日志
     */
    override fun onConsoleMessage(consoleMessage: ConsoleMessage): Boolean {
        LogUtils.d(TAG, "onConsoleMessage() -> ${consoleMessage.message()}")
        return super.onConsoleMessage(consoleMessage)
    }


    override fun onShowFileChooser(webview: WebView?, chooseFileCallBack: ValueCallback<Array<Uri>>?, p2: FileChooserParams?): Boolean {
        mChooseFilesCallback = chooseFileCallBack
        mWebViewCallBack?.onChooseImgByWebform(9)
        return true

    }


    override fun onProgressChanged(p0: WebView?, progress: Int) {
        mWebViewCallBack?.onProgressChange(progress)
    }

    override fun onReceivedTitle(p0: WebView?, title: String?) {
        mWebViewCallBack?.onReceiveTitle(title)
    }


    fun onReceiveImg(uris: List<Uri>) {
        if (uris.isEmpty())
            return
        mChooseFilesCallback?.onReceiveValue(uris.toTypedArray())
        mChooseFilesCallback = null
    }

    fun onCancleSelectImg() {
        mChooseFilesCallback?.onReceiveValue(null)
        mChooseFilesCallback = null
    }

    fun setWebChromeClientCallBack(callBack: WebChromeClientCallBack) {
        mWebViewCallBack = callBack
    }


    interface WebChromeClientCallBack {
        fun onProgressChange(progress: Int)
        fun onReceiveTitle(title: String?)
        fun onChooseImgByWebform(maxSize: Int)
    }


}
