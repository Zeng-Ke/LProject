package com.example.lproject.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.example.lproject.toast

/**
 * author: ZK.
 * date:   On 2023-07-12.
 */
abstract class BaseFragment(@LayoutRes contentLayoutId: Int) : Fragment(contentLayoutId), IUiView {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }


    final override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onViewCreated(savedInstanceState)
    }

    protected abstract fun onViewCreated(savedInstanceState: Bundle?)

    override fun showLoading() {
    }

    override fun dismissLoading() {
    }

    override fun onException(t: Throwable) {
       toast(t.message.toString())
    }

}