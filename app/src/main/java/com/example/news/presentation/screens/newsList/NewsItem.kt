package com.example.news.presentation.screens.newsList

import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.news.domain.model.Article
import com.example.news.navigation.AppScreens

@Composable
fun NewsItem(
    article: Article,
    navController: NavController
    ) {
    Card(
        modifier = Modifier
            .clickable {
                article.url.let { url ->
                    val endCodeUrl = Uri.encode(url)
                    navController.navigate(AppScreens.WebViewScreen.createRoute(endCodeUrl))
                }
            }
            .padding(8.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                text = article.title,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = article.description ?: "",
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Source: ${article.source}",
                fontStyle = FontStyle.Italic,
                fontSize = 12.sp
            )
        }
    }
}