package com.acr.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.acr.base.extensions.createViewModel
import com.acr.base.extensions.getClass
import com.acr.base.extensions.getViewDataBinding


@Suppress("UNCHECKED_CAST")
open class BaseActivity<DB : ViewDataBinding, VM : BaseViewModel>(
    private val factory: ViewModelProvider.Factory? = null
) : AppCompatActivity() {
    protected open lateinit var mViewModel: VM
    protected open lateinit var mBinding: DB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModelAndDataBinding()
    }


    private fun initViewModelAndDataBinding() {
        try {
            mBinding = getViewDataBinding(layoutInflater, 0)
            setContentView(mBinding.root)
            mBinding.lifecycleOwner = this

            mViewModel = createViewModel(factory, 1)
            lifecycle.addObserver(mViewModel)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::mBinding.isInitialized) {
            mBinding.unbind()
        }
    }
}