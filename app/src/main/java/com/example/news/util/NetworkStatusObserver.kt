package com.example.news.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.news.data.network.NetworkMonitor
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class NetworkStatusObserver(
    networkMonitor: NetworkMonitor,
    viewModel: ViewModel
) {
    val isOnline: StateFlow<Boolean> = networkMonitor.isOnline
        .stateIn(
            scope = viewModel.viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = true
        )
}
