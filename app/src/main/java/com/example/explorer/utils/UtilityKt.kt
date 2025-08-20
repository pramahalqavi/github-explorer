package com.example.explorer.utils

import android.view.Window
import androidx.core.view.WindowCompat

fun Window.setLightStatusBar(isLight: Boolean) {
    WindowCompat.getInsetsController(this, decorView).isAppearanceLightStatusBars = isLight
}