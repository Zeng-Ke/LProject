package com.example.lproject.base.webview

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.net.http.SslError
import android.os.Build
import android.os.Environment
import android.util.AttributeSet
import android.util.Log
import android.view.ViewGroup
import android.webkit.ConsoleMessage
import android.webkit.SslErrorHandler
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.blankj.utilcode.util.ThreadUtils.runOnUiThread
import com.example.lproject.toast
import com.github.lzyzsd.jsbridge.BridgeWebView
import com.github.lzyzsd.jsbridge.BridgeWebViewClient
import com.github.lzyzsd.jsbridge.DefaultHandler
import java.io.File
import java.io.FileOutputStream

/**
 * author: ZK.
 * date:   On 2023-7-24.
 */
open class BaseWebView : BridgeWebView,LifecycleEventObserver {


    constructor(context: Context) : super(context) {
        initConfiguration()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initConfiguration()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle) {
        initConfiguration()
    }


    private fun initConfiguration() {
        WebUtil.defaultSetting(this)
        setDefaultHandler(DefaultHandler())
    }





    private fun saveInAlbum(bitmap: Bitmap, fileName: String) {
        var fileOutputStream: FileOutputStream? = null
        try {
            val file = File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM),
                fileName
            ) //  路径:/storage/emulated/0/DCIM
            fileOutputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream)
            fileOutputStream.flush()
            fileOutputStream.close()
            runOnUiThread {
                context.sendBroadcast(
                    Intent(
                        Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                        Uri.fromFile(file)
                    )
                )
                toast("保存成功")
            }
        } catch (e: java.lang.Exception) {
            runOnUiThread { toast("保存失败${e.message}") }
        } finally {
            fileOutputStream?.close()
        }
    }


    override fun canGoBack(): Boolean {
        if (super.canGoBack()) {
            val list = copyBackForwardList()
            if (list.size <= 2) {
                return !EMPTY_PAGE.equals(list.currentItem?.url)
            } else {
                if (list.getItemAtIndex(1).url.equals(EMPTY_PAGE))
                    return false
                return true
            }
        } else return false
    }


    override fun goBack() {
        val list = copyBackForwardList()
        var firstEmptyIndex = -1
        for (i in 0 until list.size) {
            if (list.getItemAtIndex(i).url.equals(EMPTY_PAGE)) {
                firstEmptyIndex = i
                break
            }
        }
        if (firstEmptyIndex != -1) { //存在网页加载错误页，需要返回到上一个正常的网页
            if (list.currentIndex < firstEmptyIndex)
                super.goBack()
            else
                goBackOrForward((list.currentIndex - (firstEmptyIndex - 2)) * -1)
        } else super.goBack()

    }

    fun  setLifecycleOwner(owner: LifecycleOwner){
        owner.lifecycle.addObserver(this)
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
       if (event ==Lifecycle.Event.ON_DESTROY){
           destroy()
       }
    }


    override fun destroy() {
        release()
        super.destroy()
    }

    open fun release() {
        (parent as ViewGroup?)?.removeView(this)
        removeAllViews()
        stopLoading()
        webChromeClient = null
        webViewClient = WebViewClient()
        loadUrl(EMPTY_PAGE)
        clearHistory()

    }


    companion object {
        private const val EMPTY_PAGE = "about:blank"
    }






}