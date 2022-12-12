package com.acr.mvvm

data class Response<T>(
    var code: Int,
    var data: T,
    var message: String
)
