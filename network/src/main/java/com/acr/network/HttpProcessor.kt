package com.acr.network

interface HttpProcessor {
    suspend fun get(url: String, headers: Map<String, String?>?, params: Map<String, Any?>?): String

    suspend fun post(url: String, headers: Map<String, String?>?, params: Map<String, Any?>?): String
}