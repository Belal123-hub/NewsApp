// com.example.news.presentation.screens.history.HistoryItem.kt
package com.example.news.presentation.screens.history

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.news.domain.model.HistoryArticles
import com.example.news.navigation.AppScreens

@Composable
fun HistoryItem(
    item: HistoryArticles,
    navController: NavController
) {
    val uiModel = item.toUiModel()

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                navController.navigate(AppScreens.WebViewScreen.createRoute(uiModel.encodedUrl))
            },
        elevation = CardDefaults.cardElevation(4.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        ) {
            uiModel.imageUrl?.let { imageUrl ->
                AsyncImage(
                    model = imageUrl,
                    contentDescription = null,
                    contentScale = androidx.compose.ui.layout.ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f))
                    .padding(12.dp)
            ) {
                Text(
                    text = uiModel.title,
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White
                )

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = uiModel.formattedSource,
                    style = MaterialTheme.typography.bodySmall,
                    fontSize = 12.sp,
                    color = Color.White,
                    fontStyle = FontStyle.Italic
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = uiModel.formattedDate,
                    style = MaterialTheme.typography.bodySmall,
                    fontSize = 12.sp,
                    color = Color.White,
                    fontStyle = FontStyle.Italic
                )
            }
        }
    }
}
