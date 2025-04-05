package com.example.news.presentation.screens.history

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.news.data.network.NetworkMonitor
import com.example.news.domain.model.HistoryArticles
import com.example.news.domain.usecase.GetHistoryUseCase
import com.example.news.presentation.screens.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val getHistoryUseCase: GetHistoryUseCase,
    networkMonitor: NetworkMonitor
) : BaseViewModel(networkMonitor) {
    val historyPagingData: Flow<PagingData<HistoryArticles>> =
        getHistoryUseCase().cachedIn(viewModelScope)
}