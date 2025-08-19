package com.example.explorer

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class GithubExplorerApp : Application() {
    override fun onCreate() {
        super.onCreate()
        // Initialize any global resources or configurations here
    }
}