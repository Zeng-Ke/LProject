package com.example.lproject

import android.os.Bundle
import android.widget.TextView
import com.example.lproject.base.BaseFragment
import com.example.lproject.data.WxArticleBean
import com.example.lproject.ktx.argument
import com.example.lproject.ktx.argumentNullable

/**
 * author: ZK.
 * date:   On 2023-07-19.
 */
class MFragment : BaseFragment(R.layout.fragment_m) {

    private var wxarticle: String by argument()

    companion object {
        fun newInstance(wxArticleBean: String) = MFragment().apply {
            this.wxarticle = wxArticleBean
        }
    }


    override fun onViewCreated(savedInstanceState: Bundle?) {
        val tvTest = requireView().findViewById<TextView>(R.id.tv_test)
        tvTest.text = wxarticle.toString()
    }


    fun  setWxArticleBean(wxArticleBean: String){
        val tvTest = requireView().findViewById<TextView>(R.id.tv_test)
        wxarticle = wxArticleBean
        tvTest.text = wxarticle
    }
}