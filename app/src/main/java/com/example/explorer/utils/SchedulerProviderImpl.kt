package com.example.explorer.utils

import kotlinx.coroutines.Dispatchers

class SchedulerProviderImpl : SchedulerProvider {
    override fun io() = Dispatchers.IO

    override fun default() = Dispatchers.Default

    override fun ui() = Dispatchers.Main
}