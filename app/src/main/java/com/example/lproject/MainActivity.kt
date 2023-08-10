package com.example.lproject

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.lproject.base.BaseActivity
import com.example.lproject.databinding.ActivityMainBinding
import com.example.lproject.ktx.applicationViewModels
import com.example.lproject.ktx.viewBinding
import com.example.lproject.viewmodel.AppGlobalViewModel
import com.example.lproject.viewmodel.MainViewModel
import com.yixinli.base.mvvm.collectIn
import com.yixinli.base.mvvm.collectWithBuilder
import com.yixinli.base.mvvm.launchWithLoading
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch

class MainActivity : BaseActivity() {


    //private lateinit var binding: ActivityMainBinding
    private val binding by viewBinding(ActivityMainBinding::bind)
    private val viewModel: MainViewModel by viewModels()
    private val  appModel: AppGlobalViewModel by applicationViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)
        binding.tvTest.text = "sssss"

        val mFragment = MFragment.newInstance("MFragment.newInstance2222222")
        supportFragmentManager.beginTransaction().add(R.id.fragment_container_view, mFragment).commit()

        viewModel.uiState.collectWithBuilder(this) {
            onSuccess = {
                binding.tvTest.text = it.toString() + "\n\n\n"
                mFragment.setWxArticleBean(it.toString())
            }
        }
        viewModel.myTitleState.collectIn(this) {
            binding.tv2.text = this
        }

        viewModel.articleAllListBeanState.collectWithBuilder(this) {
            onSuccess = {
                binding.tvTest.text = it.toString() + "\n\n\n"
                //mFragment.setWxArticleBean(it.toString())
            }
            onFailed = { code, msg ->
                binding.tvTest.text = msg
            }
            onError = {
                binding.tvTest.text = it.toString()
            }
        }

        binding.viewM.setOnClickListener {
            MDialogFragment().show(supportFragmentManager, MDialogFragment::class.java.name)
            binding.viewM.setContent("viewM")
        }

        binding.tvTest.setOnClickListener {
            // launchWithLoading(viewModel::request)
            //ActivityUtils.startActivity(SecondActivity::class.java)

            launchWithLoading {
                viewModel.requestArticleList(true)
            }

        }
    }

    fun te(s: String) {
        Log.d("=====${Thread.currentThread()}", s)
    }


    fun test(str: String, m: (String) -> String) {
        val job = lifecycleScope.launch {
            flow {
                delay(100)
                //  1/0
                emit("hahhha")
            }.flowOn(Dispatchers.IO)
                .onCompletion {
                    Log.d("=====${Thread.currentThread()}", "${this}====${it.toString()}")
                }.collect {
                    Log.d("=====${Thread.currentThread()}", it)
                }
        }

    }
}