package com.example.lproject

import android.app.Activity
import android.app.Application
import androidx.viewpager.widget.ViewPager.OnAdapterChangeListener
import com.blankj.utilcode.util.LogUtils
import me.jessyan.autosize.AutoSizeConfig
import me.jessyan.autosize.onAdaptListener
import me.jessyan.autosize.unit.Subunits

/**
 * author: ZK.
 * date:   On 2023-07-20.
 */
object AppInitUtils {

    fun  init(application: Application){
        configScreenAdaptUnits()
    }

    /**
     * 屏幕适配单位配置： 设置以MM为屏幕尺寸单位
     * 详细： https://github.com/JessYanCoding/AndroidAutoSize/blob/master/demo-subunits/src/main/java/me/jessyan/autosize/demo/subunits/BaseApplication.java
     */
    private fun configScreenAdaptUnits() {
        AutoSizeConfig.getInstance().getUnitsManager()
            .setSupportDP(false)
            .setSupportSubunits(Subunits.MM)
    }

}