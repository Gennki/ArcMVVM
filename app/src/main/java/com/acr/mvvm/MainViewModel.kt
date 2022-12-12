package com.acr.mvvm

import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.viewModelScope
import com.acr.base.BaseViewModel
import com.acr.network.HttpHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel : BaseViewModel() {
    val contentFlow = MutableSharedFlow<String>()

    fun getContent() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val string = HttpHelper.Builder().url("https://www.baidu.com/").build().get<String>()
                contentFlow.emit(string)
            }
        }
    }
}