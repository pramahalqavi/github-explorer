package com.example.explorer

import com.example.explorer.utils.SchedulerProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher

class MockSchedulerProvider(val testDispatcher: TestDispatcher = UnconfinedTestDispatcher()) : SchedulerProvider {
    override fun io(): CoroutineDispatcher = testDispatcher

    override fun default(): CoroutineDispatcher = testDispatcher

    override fun ui(): CoroutineDispatcher = testDispatcher
}