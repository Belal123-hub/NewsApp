package com.example.news.presentation.screens.history

import com.example.news.data.network.NetworkMonitor
import com.example.news.domain.usecase.GetHistoryUseCase
import io.mockk.mockk
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class HistoryViewModelTest {

    // Minimal mocks
    private lateinit var getHistoryUseCase: GetHistoryUseCase
    private lateinit var networkMonitor: NetworkMonitor
    private lateinit var viewModel: HistoryViewModel

    @Before
    fun setUp() {
        getHistoryUseCase = mockk(relaxed = true)
        networkMonitor = mockk(relaxed = true)
        viewModel = HistoryViewModel(getHistoryUseCase, networkMonitor)
    }
// Checks that the HistoryViewModel initializes without crashing
    @Test
    fun `viewModel initializes successfully`() {
        assertNotNull(viewModel)
    }
// Ensures that the PagingData property is present in the ViewModel
    @Test
    fun `historyPagingData exists`() {
        assertNotNull(viewModel.historyPagingData)
    }
// Ensures that the isOnline Flow exists in the ViewModel
    @Test
    fun `isOnline exists`() {
        assertNotNull(viewModel.isOnline)
    }
}