package com.example.news

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.example.news.navigation.AppNavigation
import com.example.news.presentation.screens.history.HistoryViewModel
import com.example.news.presentation.screens.newsList.NewsListScreen
import com.example.news.presentation.screens.newsList.NewsListViewModel
import com.example.news.presentation.theme.ThemeUiState
import com.example.news.presentation.theme.ThemeViewModel
import com.example.news.ui.theme.NewsTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val newsViewModel: NewsListViewModel by viewModels()
    private val historyViewModel: HistoryViewModel by viewModels()
    private val themeViewModel: ThemeViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        var keepSplashScreen = true
        super.onCreate(savedInstanceState)
        splashScreen.setKeepOnScreenCondition {keepSplashScreen}
        lifecycleScope.launch {
            delay(5000)
            keepSplashScreen=false
        }
        enableEdgeToEdge()
        setContent {
            val themeUiState by themeViewModel.uiState.collectAsState()

            val isDarkTheme = when (themeUiState) {
                is ThemeUiState.Dark -> true
                is ThemeUiState.Light -> false
            }

            NewsTheme(darkTheme = isDarkTheme) {
                Scaffold(modifier = Modifier.fillMaxSize()) { padding ->
                    AppNavigation(
                        newsViewModel,
                        historyViewModel,
                        padding,
                        themeViewModel
                    )
                }
            }
        }


    }
}
