package com.example.news.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.news.presentation.screens.newsList.NewsListScreen
import com.example.news.presentation.screens.newsList.NewsListViewModel
import com.example.news.presentation.screens.webView.WebViewScreen

@Composable
fun AppNavigation(
    viewModel:NewsListViewModel,
    padding:PaddingValues
){
    val navController = rememberNavController()
    val news = viewModel.newsPagingData.collectAsLazyPagingItems()
    NavHost(navController = navController, startDestination = AppScreens.NewsScreen.route){
        composable(route = AppScreens.NewsScreen.route){
            NewsListScreen(
                navController = navController,
                news = news,
                padding = padding
            )
        }
        composable(
            route = AppScreens.WebViewScreen.route,
            arguments = listOf(navArgument("url") { type = NavType.StringType })
        ){ backStackEntery ->
            val url = backStackEntery.arguments?.getString("url")
            WebViewScreen(
                navController = navController,
                url = url
                )
        }
    }
}