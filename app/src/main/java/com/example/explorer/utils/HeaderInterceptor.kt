package com.example.explorer.utils

import com.example.githubexplorer.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val newRequest = originalRequest.newBuilder()
            .header("User-Agent", "request")
//            .header("Authorization", "Bearer ${BuildConfig.GITHUB_API_TOKEN}")
            .build()
        return chain.proceed(newRequest)
    }
}