package com.acr.network

import com.google.gson.Gson

class HttpHelper private constructor() {
    companion object {
        @JvmStatic
        private lateinit var httpProcessor: HttpProcessor

        fun init(httpProcessor: HttpProcessor) {
            HttpHelper.httpProcessor = httpProcessor
        }
    }

    private var headers: HashMap<String, String?>? = null
    private var params: HashMap<String, Any?>? = null
    private var url: String = ""
    lateinit var gson: Gson


    private constructor(url: String, headers: HashMap<String, String?>?, params: HashMap<String, Any?>?) : this() {
        this.url = url
        this.headers = headers
        this.params = params
        gson = Gson()
    }

    suspend inline fun <reified T> get(): T {
        val result = doGet()
        return if (T::class.java == String::class.java) {
            result as T
        } else {
            gson.fromJson(result, T::class.java)
        }
    }

    suspend inline fun <reified T> post(): T {
        val result = doPost()
        return if (T::class.java == String::class.java) {
            result as T
        } else {
            gson.fromJson(result, T::class.java)
        }
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