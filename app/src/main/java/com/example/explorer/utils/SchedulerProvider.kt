package com.example.explorer.utils

import kotlinx.coroutines.CoroutineDispatcher

interface SchedulerProvider {
    fun io(): CoroutineDispatcher
    fun default(): CoroutineDispatcher
    fun ui(): CoroutineDispatcher
}