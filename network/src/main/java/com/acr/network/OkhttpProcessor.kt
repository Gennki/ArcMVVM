package com.acr.network

import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request

class OkhttpProcessor : HttpProcessor {
    private var okHttpClient: OkHttpClient = OkHttpClient()

    fun setOkhttpClient(okHttpClient: OkHttpClient) {
        this.okHttpClient = okHttpClient
    }

    override suspend fun get(url: String, headers: Map<String, String?>?, params: Map<String, Any?>?): String {
        val builder = Request.Builder()
        headers?.let {
            for ((key, value) in headers) {
                value?.let { builder.header(key, value) }
            }
        }

        params?.let {
            val stringBuilder = StringBuilder(url)
            stringBuilder.append("?")
            for ((key, value) in params) {
                value?.let {
                    stringBuilder.append(key).append("=").append(value).append("&")
                }
            }
            stringBuilder.substring(0, stringBuilder.length - 1)
            builder.url(stringBuilder.toString())
        } ?: let {
            builder.url(url)
        }
        val request = builder.build()
        val call = okHttpClient.newCall(request)
        val response = call.execute()
        val result = if (response.isSuccessful) {
            response.body?.string() ?: ""
        } else {
            response.message
        }
        return result
    }

    override suspend fun post(url: String, headers: Map<String, String?>?, params: Map<String, Any?>?): String {
        val builder = Request.Builder()
        builder.url(url)
        headers?.let {
            for ((key, value) in headers) {
                value?.let { builder.header(key, value) }
            }
        }

        params?.let {
            val formBodyBuilder = FormBody.Builder()
            for ((key, value) in params) {
                value?.let {
                    formBodyBuilder.add(key, value.toString())
                }
            }
            builder.post(formBodyBuilder.build())
        }
        val request = builder.build()
        val call = okHttpClient.newCall(request)
        val response = call.execute()
        val result = if (response.isSuccessful) {
            response.body?.string() ?: ""
        } else {
            response.message
        }
        return result
    }


}