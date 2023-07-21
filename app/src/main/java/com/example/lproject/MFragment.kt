package com.example.lproject

import android.os.Bundle
import android.widget.TextView
import com.blankj.utilcode.util.LogUtils
import com.example.lproject.base.BaseApp
import com.example.lproject.base.BaseFragment
import com.example.lproject.data.WxArticleBean
import com.example.lproject.databinding.FragmentMBinding
import com.example.lproject.ktx.argument
import com.example.lproject.ktx.argumentNullable
import com.example.lproject.ktx.mm2px
import com.example.lproject.ktx.px2mm
import com.example.lproject.ktx.viewBinding
import me.jessyan.autosize.utils.AutoSizeUtils

/**
 * author: ZK.
 * date:   On 2023-07-19.
 */
class MFragment : BaseFragment(R.layout.fragment_m) {

    private val binding by viewBinding(FragmentMBinding::bind)
    private var wxarticle: String by argument()

    companion object {
        fun newInstance(wxArticleBean: String) = MFragment().apply {
            this.wxarticle = wxArticleBean
        }
    }


    override fun onViewCreated(savedInstanceState: Bundle?) {
        binding.tvTest.text = wxarticle

        LogUtils.dTag("====",AutoSizeUtils.mm2px(BaseApp.getInstance(),500f))
        LogUtils.dTag("====",100.mm2px() )
        LogUtils.dTag("====",100.px2mm())
    }


    fun setWxArticleBean(wxArticleBean: String) {
        wxarticle = wxArticleBean
        binding.tvTest.text = wxarticle
    }
}