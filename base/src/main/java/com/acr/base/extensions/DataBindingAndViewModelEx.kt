@file:Suppress("UNCHECKED_CAST")

package com.acr.base.extensions

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.ComponentActivity
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.acr.base.BaseViewModel
import java.lang.reflect.ParameterizedType

/**
 * 获取DataBinding对象
 */
fun <DB : ViewDataBinding> Any.getViewDataBinding(inflater: LayoutInflater, position: Int = 0): DB {
    val dataBindingClass = getClass<DB>(position)
    val inflate = dataBindingClass.getDeclaredMethod("inflate", LayoutInflater::class.java)
    return inflate.invoke(null, inflater) as DB
}

/**
 * 获取DataBinding对象
 */
fun <DB : ViewDataBinding> Any.getViewDataBinding(inflater: LayoutInflater, container: ViewGroup?, position: Int = 0): DB {
    val dataBindingClass = getClass<DB>(position)
    val inflate = dataBindingClass.getDeclaredMethod("inflate", LayoutInflater::class.java, ViewGroup::class.java, Boolean::class.java)
    return inflate.invoke(null, inflater, container, false) as DB
}

/**
 * 获取Class上指定位置的泛型类型
 */
fun <T> Any.getClass(position: Int): Class<T> {
    return (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments.filterIsInstance<Class<T>>()[position]
}

/**
 * Activity中获取ViewModel
 */
fun <VM : BaseViewModel> ComponentActivity.createViewModel(
    factory: ViewModelProvider.Factory? = null,
    position: Int
): VM {
    val viewModelClass = getClass<VM>(position)
    return factory?.let {
        ViewModelProvider(
            this,
            factory
        )[viewModelClass]
    } ?: let {
        ViewModelProvider(this)[viewModelClass]
    }
}

/**
 * Fragment中获取ViewModel，不和Activity共享
 */
fun <VM : BaseViewModel> Fragment.createViewModel(
    factory: ViewModelProvider.Factory? = null,
    position: Int
): VM {
    val viewModelClass = getClass<VM>(position)
    return factory?.let {
        ViewModelProvider(
            this,
            factory
        )[viewModelClass]
    } ?: let {
        ViewModelProvider(this)[viewModelClass]
    }
}

/**
 * Fragment中获取ViewModel，和Activity共享
 */
fun <VM : BaseViewModel> Fragment.createActivityViewModel(
    factory: ViewModelProvider.Factory? = null,
    position: Int
): VM {
    val viewModel = getClass<VM>(position)
    return factory?.let {
        ViewModelProvider(
            requireActivity(),
            factory
        )[viewModel]
    } ?: let {
        ViewModelProvider(requireActivity())[viewModel]
    }
}

