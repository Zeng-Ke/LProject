package com.example.lproject.ktx

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.DisplayMetrics
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.annotation.ColorRes
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment


/**
 * author: ZK.
 * date:   On 2023-07-24.
 */

fun Activity.hideSoftKeyboard(focusView: View? = null) {
    val focusV = focusView ?: currentFocus
    focusV?.apply {
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
    }
}

fun Activity.hardToHideKeyboard() {
    val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    if (imm.isActive) {
        if (this.getCurrentFocus()?.getWindowToken() != null) {
            imm.hideSoftInputFromWindow(this.getCurrentFocus()!!.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS)
        }
    }
}


fun Activity.showSoftKeyboard(editText: EditText) {
    editText.postDelayed({
        editText.requestFocus()
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.showSoftInput(editText, InputMethodManager.SHOW_FORCED)
    }, 30)
}

fun Activity.getContentView(): ViewGroup {
    return this.findViewById(android.R.id.content) as ViewGroup
}

fun AppCompatActivity.replaceFragmentInActivity(fragment: Fragment, @IdRes frameId: Int) {
    supportFragmentManager.transact {
        replace(frameId, fragment)
    }
}

fun AppCompatActivity.addFragmentToActivity(fragment: Fragment, tag: String) {
    supportFragmentManager.transact {
        add(fragment, tag)
    }
}

fun AppCompatActivity.setWindowBackGround(@ColorRes colorRes: Int) {
    window.getDecorView().setBackgroundResource(colorRes)
}

fun AppCompatActivity.launchToBrowser(url: String) {
    val intent = Intent()
    intent.setData(Uri.parse(url))
    intent.setAction(Intent.ACTION_VIEW);
    startActivity(intent) //启动浏览器
}


fun Activity.getDisplayedHeight(): Int {
    return resources.displayMetrics.heightPixels
}


fun Activity.getDisplayedWidth(): Int {
    return resources.displayMetrics.widthPixels
}


fun Activity.getScrenWidth(): Int {
    val outMetrics = DisplayMetrics()
    windowManager.defaultDisplay.getRealMetrics(outMetrics)
    return outMetrics.widthPixels
}



fun Activity.getScrenHeight(): Int {
    val outMetrics = DisplayMetrics()
    windowManager.defaultDisplay.getRealMetrics(outMetrics)
    return outMetrics.heightPixels
}



fun  Activity.openByBrowser(url:String){
    try {
        val  intent =  Intent()
        intent.setAction("android.intent.action.VIEW")
        val content_url = Uri.parse(url)
        intent.setData(content_url)
        startActivity(intent)
    }catch (e:Exception){
        e.printStackTrace()
    }

}









