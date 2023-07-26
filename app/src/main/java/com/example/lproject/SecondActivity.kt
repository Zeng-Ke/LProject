package com.example.lproject

import android.os.Bundle
import android.widget.FrameLayout
import com.example.lproject.base.BaseActivity
import com.example.lproject.base.webview.BaseWebChromeClient
import com.example.lproject.base.webview.BaseWebViewClient
import com.example.lproject.base.webview.WebViewPool
import com.example.lproject.databinding.ActivitySecondBinding
import com.example.lproject.ktx.viewBinding

/**
 * author: ZK.
 * date:   On 2023-07-20.
 */
class SecondActivity : BaseActivity() {

    companion object {
        val list = arrayListOf(
              "https://wx-box1.zixunshizhijia.com/website-h5/index.html#/articleDetail?id=329&token=token%3Alki42xteE99KNj",
            "https://pt.xinletu.love/website-h5/index.html#/articleDetail?id=14503&token=token:lkj60vlfqbhecL",
            "https://wx.zixunshizhijia.com/website-h5/index.html#/lesson/referral-traffic?channelId=2002&token=token:lkj66i6auPfJJS",
            "https://tophub.today/c/tech",
            "https://weibo.com/newlogin?tabtype=weibo&gid=102803&openLoginLayer=0&url=https%3A%2F%2Fweibo.com%2F",
            "https://juejin.cn/post/7218438764099272741?searchId=202307251036484C60ABE0E6CC60349286"
        )
        var a = 0
    }

    private val binding by viewBinding(ActivitySecondBinding::bind)

    private var webView: LWebView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        webView = WebViewPool.instance.requireWebView(this).apply {
            binding.frWebContainer.addView(this, FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
            setLifecycleOwner(this@SecondActivity)
            webViewClient = BaseWebViewClient(this)
            webChromeClient = BaseWebChromeClient()
            val url = list.removeAt(0)
            loadUrl(url)
            list.add(url)
        }

    }

    override fun onDestroy() {
        super.onDestroy()

    }

}