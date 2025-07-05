package com.example.news.presentation.screens.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.news.data.network.NetworkMonitor
import com.example.news.domain.model.HistoryArticles
import com.example.news.domain.usecase.GetHistoryUseCase
import com.example.news.util.NetworkStatusObserver
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val getHistoryUseCase: GetHistoryUseCase,
    networkMonitor: NetworkMonitor
) : ViewModel() {

    private val networkObserver = NetworkStatusObserver(networkMonitor, this)

    val historyPagingData: Flow<PagingData<HistoryArticles>> =
        getHistoryUseCase().cachedIn(viewModelScope)

    private val _uiState = MutableStateFlow(HistoryUiState())
    val uiState: StateFlow<HistoryUiState> = _uiState.asStateFlow()

    init {
        // Collect isOnline changes and update state
        networkObserver.isOnline
            .onEach { isOnline ->
                _uiState.update { it.copy(isOnline = isOnline) }
            }
            .launchIn(viewModelScope)
    }
}
