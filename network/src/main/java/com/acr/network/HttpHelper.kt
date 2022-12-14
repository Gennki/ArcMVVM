package com.acr.network

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class HttpHelper private constructor() {
    companion object {
        private lateinit var httpProcessor: HttpProcessor
        private lateinit var gson: Gson

        fun init(httpProcessor: HttpProcessor) {
            HttpHelper.httpProcessor = httpProcessor
            gson = Gson()
        }
    }

    private var headers: HashMap<String, String?>? = null
    private var params: HashMap<String, Any?>? = null
    private var url: String = ""


    private constructor(url: String, headers: HashMap<String, String?>?, params: HashMap<String, Any?>?) : this() {
        this.url = url
        this.headers = headers
        this.params = params
    }

    suspend inline fun <reified T> get(): T {
        val result = doGet()
        val type: Type = object : TypeToken<T>() {}.type
        return Gson().fromJson(result, type)
    }

    suspend inline fun <reified T> post(): T {
        val result = doPost()
        val type: Type = object : TypeToken<T>() {}.type
        return Gson().fromJson(result, type)
    }

    suspend fun doGet(): String {
        return httpProcessor.get(url, headers, params)
    }

    suspend fun doPost(): String {
        return httpProcessor.post(url, headers, params)
    }


    class Builder {
        private var headers: HashMap<String, String?>? = null
        private var params: HashMap<String, Any?>? = null
        private var url: String = ""

        fun url(url: String): Builder {
            this.url = url
            return this
        }

        fun header(key: String, value: String?): Builder {
            if (headers == null) {
                headers = HashMap()
            }
            headers?.set(key, value)
            return this
        }

        fun params(key: String, value: String?): Builder {
            if (params == null) {
                params = HashMap()
            }
            params?.set(key, value)
            return this
        }

        fun build(): HttpHelper {
            return HttpHelper(url, headers, params)
        }
    }

}