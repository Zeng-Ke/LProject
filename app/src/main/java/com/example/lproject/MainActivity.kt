package com.example.lproject

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import com.example.lproject.base.BaseActivity
import com.example.lproject.data.WxArticleBean
import com.example.lproject.databinding.ActivityMainBinding
import com.example.lproject.ktx.collectIn
import com.example.lproject.ktx.launchWithLoading
import com.example.lproject.net.ResultBuilder
import com.example.lproject.viewmodel.MainViewModel

class MainActivity : BaseActivity() {


    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.tvTest.text = "sssss"
        val builder = ResultBuilder<List<WxArticleBean>>()

        viewModel.uiState.collectIn(this) {
            Log.d("=======","2")
            onSuccess = {
                binding.tvTest.text = it.toString()
            }
            onFailed = { code, msg ->
                binding.tvTest.text = msg
            }
            onError ={
                binding.tvTest.text = it.toString()
            }
        }


        binding.tvTest.setOnClickListener {
            launchWithLoading(viewModel::request)
        }
        test("a"){
            it + "11"
        }
    }


    fun test(str:String, m :(String)->String){
        Log.d("==",m.invoke(str))
        Log.d("==",  m(str))

    }
}