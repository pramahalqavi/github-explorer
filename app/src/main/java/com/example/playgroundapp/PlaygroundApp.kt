package com.example.playgroundapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class PlaygroundApp : Application() {
    override fun onCreate() {
        super.onCreate()
        // Initialize any global resources or configurations here
    }
}