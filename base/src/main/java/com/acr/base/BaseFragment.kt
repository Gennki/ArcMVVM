package com.acr.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.acr.base.extensions.createActivityViewModel
import com.acr.base.extensions.createViewModel
import com.acr.base.extensions.getViewDataBinding

@Suppress("UNCHECKED_CAST")
open class BaseFragment<DB : ViewDataBinding, VM : BaseViewModel>(
    private val shareViewModel: Boolean = false,
    private val factory: ViewModelProvider.Factory? = null
) : Fragment() {
    protected open lateinit var mBinding: DB
    protected open lateinit var mViewModel: VM

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = getViewDataBinding(inflater, container, 0)

        mViewModel = if (shareViewModel) {
            createActivityViewModel(factory, 1)
        } else {
            createViewModel(factory, 1)
        }
        return mBinding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::mBinding.isInitialized) {
            mBinding.unbind()
        }
    }

}