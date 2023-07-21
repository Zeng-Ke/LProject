package com.example.lproject

import com.example.lproject.base.BaseApp

/**
 * author: ZK.
 * date:   On 2023-07-21.
 */
class LProjectApplication : BaseApp() {

    override fun onCreate() {
        super.onCreate()
        AppInitUtils.init(this)
    }
}