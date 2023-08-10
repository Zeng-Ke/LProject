package com.example.lproject.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * author: ZK.
 * date:   On 2023-08-10.
 */
class AppGlobalViewModel(application: Application) : AndroidViewModel(application) {

    var a = MutableStateFlow(0)




}