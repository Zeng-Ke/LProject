package com.example.lproject.ktx

import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

/**
 * author: ZK.
 * date:   On 2023-07-24.
 */


fun Fragment.hideSoftKeyboard() {
    activity?.hideSoftKeyboard()
}

fun DialogFragment.hideSoftKeyboard() {
    val dialog = getDialog()
    activity?.hideSoftKeyboard(dialog?.currentFocus)
}

fun DialogFragment.hideSoftKeyboard(focuseView: View) {
    activity?.hideSoftKeyboard(focuseView)
}

inline fun FragmentManager.transact(action: FragmentTransaction.() -> Unit) {
    beginTransaction().apply { action() }.commit()
}


val Fragment.screenWidth: Int
    get() = resources.displayMetrics.widthPixels

val Fragment.screenHeight: Int
    get() = resources.displayMetrics.heightPixels


val Fragment.screenWidthMM: Int
    get() = screenWidth.px2mm().toInt()

val Fragment.screenHeightMM: Int
    get() = screenHeight.px2mm().toInt()


fun Fragment.hideDialogFragment(tag: String) {
    val childFragment = childFragmentManager.findFragmentByTag(tag)
    if (childFragment is DialogFragment)
        childFragment.dismissAllowingStateLoss()
}


