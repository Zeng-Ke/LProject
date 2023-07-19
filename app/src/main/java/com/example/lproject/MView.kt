package com.example.lproject

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import com.example.lproject.databinding.ViewMBinding
import com.example.lproject.ktx.viewBinding

/**
 * author: ZK.
 * date:   On 2023-07-19.
 */
class MView(context: Context, attributeSet: AttributeSet? = null) : LinearLayout(context, attributeSet) {

    private val binding by viewBinding(ViewMBinding::bind)

    init {
        inflate(context, R.layout.view_m, this)
    }

    fun setContent(content: String) {
        binding.tvTest.text = content
    }


}