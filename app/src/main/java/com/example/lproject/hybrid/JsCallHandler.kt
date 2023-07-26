package com.example.lproject.hybrid

import android.content.Context
import com.example.lproject.LWebView
import com.example.lproject.hybrid.data.JsCallNativeBean
import com.github.lzyzsd.jsbridge.CallBackFunction

/**
 * author: ZK.
 * date:   On 2023-07-24.
 */
class JsCallHandler(val context: Context, val webview: LWebView) {

    private var mJsCallActions: JsCallActions? = null
    interface JsCallActions {}


    fun handle(jsCallNativeBean: JsCallNativeBean, function: CallBackFunction) {

    }

    fun setJsCallActions(jsCallActions: JsCallActions) {
        mJsCallActions = jsCallActions
    }

    fun destroy() {

    }
}