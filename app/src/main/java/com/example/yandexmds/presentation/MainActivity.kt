package com.example.yandexmds.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Scaffold
import androidx.navigation.compose.rememberNavController
import com.example.yandexmds.presentation.navigation.BottomNavigationBar
import com.example.yandexmds.presentation.navigation.Navigation
import com.example.yandexmds.ui.theme.YandexMDSTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            YandexMDSTheme {
                val navController = rememberNavController()
                Scaffold(
                    bottomBar = {
                        BottomNavigationBar(
                            navController = navController
                        )
                    }
                ) { outerPadding ->
                    Navigation(navController, outerPadding)
                }

            }
        }
    }
}

