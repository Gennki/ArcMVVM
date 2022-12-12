package com.acr.mvvm

import android.app.Application
import com.acr.network.HttpHelper
import com.acr.network.OkhttpProcessor


class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        HttpHelper.init(OkhttpProcessor())
    }
}