package com.acr.mvvm

import android.os.Bundle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.acr.base.BaseActivity
import com.acr.mvvm.databinding.ActivityMainBinding
import kotlinx.coroutines.*

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding.executePendingBindings()
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                mViewModel.contentFlow.collect {
                    mBinding.tvContent.text = "userName:${it?.userName},\nage:${it?.age},\nemail:${it?.email}"
                }
            }
        }

        mViewModel.login()


    }

}