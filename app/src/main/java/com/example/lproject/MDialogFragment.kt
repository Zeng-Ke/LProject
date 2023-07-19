package com.example.lproject

import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import com.example.lproject.databinding.DialogFragmentMBinding
import com.example.lproject.ktx.viewBinding

/**
 * author: ZK.
 * date:   On 2023-07-19.
 */
class MDialogFragment :DialogFragment(R.layout.dialog_fragment_m) {

    private val  binding   by viewBinding(DialogFragmentMBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.tvTest.text = this::class.java.name
    }

}