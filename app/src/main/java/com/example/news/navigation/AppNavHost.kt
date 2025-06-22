package com.example.news.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.news.presentation.screens.history.HistoryScreen
import com.example.news.presentation.screens.history.HistoryViewModel
import com.example.news.presentation.screens.newsList.NewsListScreen
import com.example.news.presentation.screens.newsList.NewsListViewModel
import com.example.news.presentation.screens.webView.WebViewScreen
import com.example.news.presentation.theme.ThemeViewModel

@Composable
fun AppNavigation(
    viewModel: NewsListViewModel,
    historyViewModel: HistoryViewModel,
    padding: PaddingValues,
    themeViewModel: ThemeViewModel
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = AppScreens.NewsScreen.route) {
        composable(route = AppScreens.NewsScreen.route) {
            NewsListScreen(
                navController = navController,
                newsFlow = viewModel.newsPagingData,  // pass Flow here
                padding = padding,
                viewModel = viewModel,
                themeViewModel = themeViewModel
            )
        }
        composable(
            route = AppScreens.WebViewScreen.route,
            arguments = listOf(navArgument("url") { type = NavType.StringType })
        ) { backStackEntry ->
            val url = backStackEntry.arguments?.getString("url")
            WebViewScreen(
                navController = navController,
                url = url
            )
        }
        composable(route = AppScreens.HistoryScreen.route) {
            HistoryScreen(navController, historyViewModel)
        }
    }
}
