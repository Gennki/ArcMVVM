package com.acr.network

import okhttp3.*
import java.io.IOException

class OkhttpProcessor(
    private val okHttpClient: OkHttpClient = OkHttpClient()
) : HttpProcessor {

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
        if (response.isSuccessful) {
            return response.body?.string() ?: ""
        } else {
            throw IOException(response.toString())
        }
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
        if (response.isSuccessful) {
            return response.body?.string() ?: ""
        } else {
            throw IOException(response.toString())
        }
    }


}