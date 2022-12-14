package com.acr.mvvm

import android.widget.Toast
import androidx.lifecycle.viewModelScope
import com.acr.base.BaseViewModel
import com.acr.network.HttpHelper
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel : BaseViewModel() {
    val contentFlow = MutableSharedFlow<UserBean>()

    fun login() {
        viewModelScope.launch(CoroutineExceptionHandler { coroutineContext, throwable ->
            Toast.makeText(MyApplication.instance, throwable.message, Toast.LENGTH_SHORT).show()
        }) {
            withContext(Dispatchers.IO) {
                val response = HttpHelper.Builder()
                    .url("https://mock.apifox.cn/m1/972365-0-default/login")
                    .params("userName", "张三")
                    .params("password", "123456")
                    .build()
                    .post<Response<UserBean>>()
                contentFlow.emit(response.data)
            }
        }
    }
}