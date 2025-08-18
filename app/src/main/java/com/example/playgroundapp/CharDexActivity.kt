package com.example.playgroundapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.playgroundapp.ui.theme.PlaygroundappTheme

class CharDexActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PlaygroundappTheme {
                Content()
            }
        }
    }

    @Composable
    private fun Conntent() {
        Column {
            Text("Hello CharDex!")
        }
    }
}