package com.example.news.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import com.example.news.domain.model.Article
import kotlinx.coroutines.launch


@Composable
fun ShareButton(
    onClick: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    IconButton(
        onClick = {
            coroutineScope.launch {
                onClick()
            }
        }
    ) {
        Icon(
            imageVector = Icons.Default.Share,
            contentDescription = "Share article"
        )
    }
}