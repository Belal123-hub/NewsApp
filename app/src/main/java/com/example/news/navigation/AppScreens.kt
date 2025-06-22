package com.example.news.navigation

import android.net.Uri

sealed class AppScreens(val route: String) {
    object NewsScreen : AppScreens("news_screen")

    object WebViewScreen : AppScreens("webview_screen/{url}") {
        fun createRoute(url: String): String = "webview_screen/${Uri.encode(url)}"
    }

    object HistoryScreen : AppScreens("history_screen")
}
