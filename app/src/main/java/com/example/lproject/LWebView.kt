package com.example.lproject

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.GsonUtils
import com.example.lproject.base.webview.BaseWebView
import com.example.lproject.hybrid.JsCallHandler
import com.example.lproject.hybrid.data.JsCallNativeBean
import com.example.lproject.hybrid.data.NativeCallJsBean
import me.jessyan.autosize.DefaultAutoAdaptStrategy
import me.jessyan.autosize.WrapperAutoAdaptStrategy

/**
 * author: ZK.
 * date:   On 2019-11-05.
 */
open class LWebView : BaseWebView {

    protected var mJsCallHandler: JsCallHandler? = null

    private var scrollableParent: ViewGroup? = null
    protected var jsCallAction: JsCallHandler.JsCallActions? = null


    constructor(context: Context) : super(context) {
        initBridge()
        afterInit(context)
    }

    constructor(context: Context, attributeSet: AttributeSet?) : super(context, attributeSet) {
        initBridge()
        afterInit(context)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initBridge()
        afterInit(context)
    }


    private fun initBridge() {
       // mJsCallHandler = JsCallHandler(context, this)
        registerHandler("methodForJs") { data, function ->
            val jsCallNativeBean = GsonUtils.fromJson(data,JsCallNativeBean::class.java)
            mJsCallHandler?.handle(jsCallNativeBean, function)
        }
        registerHandler("getAppVervison") { data, function ->
            val versionCode = AppUtils.getAppVersionCode()
            function.onCallBack(versionCode.toString())
        }
    }

    private fun afterInit(context: Context) {
        if (context is Activity){//解决webView初始化时，覆盖了AutoSize的配置，导致屏幕适配失效问题（webView嵌套在首页时）
            WrapperAutoAdaptStrategy(DefaultAutoAdaptStrategy()).applyAdapt(context, context)
        }
    }


    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            if (scrollableParent == null) {
                scrollableParent = findViewParentIfNeeds(this, 10)
            }
            scrollableParent?.requestDisallowInterceptTouchEvent(true)
        }
        return super.onTouchEvent(event)
    }

    override fun onOverScrolled(scrollX: Int, scrollY: Int, clampedX: Boolean, clampedY: Boolean) {
        if (clampedX) {
            if (scrollableParent == null) {
                scrollableParent = findViewParentIfNeeds(this, 10)
            }
            scrollableParent?.requestDisallowInterceptTouchEvent(false)
        }
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY)
    }


    internal fun findViewParentIfNeeds(tag: View, depth: Int): ViewGroup? {
        if (depth < 0) {
            return null
        }
        val parent = tag.parent ?: return null
        if (parent is ViewGroup) {
            if (canScrollHorizontal(parent)) {
                return parent
            } else {
                //增加最大递归深度判断，防止出现ANR或者异常
                return findViewParentIfNeeds(parent, depth - 1)
            }
        } else return null
    }

    private fun canScrollHorizontal(view: View) = view.canScrollHorizontally(100) || view.canScrollHorizontally(-100)


    fun setJsCallActions(jsCallActions: JsCallHandler.JsCallActions) {
        this.jsCallAction = jsCallActions
        mJsCallHandler?.setJsCallActions(jsCallActions)
    }

    fun tellJsWebViewShow() {
        val nativeCallJsBean = NativeCallJsBean("appWebviewShow", null)
        callHandler("methodForNative", GsonUtils.toJson(nativeCallJsBean), null)

    }

    fun tellJsWebViewHide() {
        val nativeCallJsBean = NativeCallJsBean("appWebviewHide", null)
        callHandler("methodForNative", GsonUtils.toJson(nativeCallJsBean), null)
    }


    fun callGoBack(callBack: ((String) -> Unit)? = null) {
        val nativeCallJsBean = NativeCallJsBean("goBack", null)
        callHandler("methodForNative", GsonUtils.toJson(nativeCallJsBean), callBack)
    }


    fun callJsMethod(nativeCallJsBean: NativeCallJsBean, callBack: ((String) -> Unit)? = null) {
        callHandler("methodForNative", GsonUtils.toJson(nativeCallJsBean), callBack)
    }




    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        release()
    }


    override fun release() {
        super.release()
        mJsCallHandler?.destroy()
    }


    override fun onResume() {
        super.onResume()
        tellJsWebViewShow()
    }


    override fun onPause() {
        super.onPause()
        tellJsWebViewHide()
    }







}