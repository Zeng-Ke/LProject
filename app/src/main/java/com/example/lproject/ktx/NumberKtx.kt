package com.example.lproject.ktx

import android.content.Context
import com.example.lproject.base.BaseApp
import me.jessyan.autosize.utils.AutoSizeUtils

/**
 * author: ZK.
 * date:   On 2023-07-21.
 */

fun Int.mm2px(context: Context = BaseApp.getInstance()) = AutoSizeUtils.mm2px(context, this.toFloat())

fun Int.px2mm(context: Context = BaseApp.getInstance()) = this * 25.4 / context.resources.displayMetrics.xdpi


