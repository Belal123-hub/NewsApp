package com.example.news.presentation.screens.newsList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.news.domain.model.Article
import com.example.news.domain.usecase.GetTopHeadlinesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class NewsListViewModel @Inject constructor(
    private val getTopHeadlinesUseCase: GetTopHeadlinesUseCase
) : ViewModel() {

    val newsPagingData: Flow<PagingData<Article>> = getTopHeadlinesUseCase(country = "us")
        .cachedIn(viewModelScope) // Cache the PagingData in the ViewModel scope
}