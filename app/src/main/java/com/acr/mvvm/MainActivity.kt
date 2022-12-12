package com.acr.mvvm

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.acr.base.BaseActivity
import com.acr.base.BaseViewModel
import com.acr.mvvm.databinding.ActivityMainBinding
import com.acr.network.HttpHelper
import com.acr.network.OkhttpProcessor
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.flow.*
import java.net.ResponseCache
import kotlin.coroutines.CoroutineContext

class MainActivity : BaseActivity<ActivityMainBinding, BaseViewModel>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding.executePendingBindings()
//        lifecycleScope.launchWhenResumed {
//            val flow1 = flowOf(1, 2)
//            val flow2 = flowOf("One", "Two", "Three")
//            flow2.zip(flow1){value1,value2->
//                "$value1,$value2"
//            }.onEach {
//                delay(100)
//            }.onCompletion {
//                Log.d("FlowTest","onCompletion1")
//            }.collect {
//                Log.d("FlowTest",it)
//            }
//        }
//        lifecycleScope.launchWhenResumed {
//            val flow3 = flowOf(3, 4)
//            val flow4 = flowOf("One", "Two", "Three")
//            flow3.zip(flow4){value1,value2->
//                "$value1,$value2"
//            }.onEach {
//                delay(200)
//            }.onCompletion {
//                Log.d("FlowTest","onCompletion11")
//            }.collect {
//                Log.d("FlowTest",it)
//            }
//        }

//        lifecycleScope.launch {
//            repeatOnLifecycle(Lifecycle.State.RESUMED){
//                Log.d("FlowTest","repeatOnLifecycle")
//            }
//        }

        val httpProcessor = OkhttpProcessor()
        HttpHelper.init(httpProcessor)

        lifecycleScope.launch(CoroutineExceptionHandler { context: CoroutineContext, exception: Throwable ->

        }) {
            val job1 = async {
                HttpHelper.Builder().url("https://www.baidu.com/").header("", "").params("", "").build().get<String>()
            }

            val job2 = async {
                HttpHelper.Builder().url("https://www.baidu.com/").header("", "").params("", "").build().get<String>()
            }
            job1.await()
            job2.await()
        }

    }

}