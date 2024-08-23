package com.example.yandexmds.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.yandexmds.presentation.screens.main.MainScreen
import com.example.yandexmds.ui.theme.YandexMDSTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            YandexMDSTheme {
//                Navigation()
                MainScreen()
            }
        }
    }
}

