package com.example.news

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import com.example.news.presentation.NewsViewModel

class MainActivity : ComponentActivity() {
    private val newsViewModel: NewsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Call fetchNews when the activity starts
        newsViewModel.fetchNews()
    }
}
