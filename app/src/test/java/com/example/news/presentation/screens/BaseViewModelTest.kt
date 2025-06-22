package com.example.news.presentation.screens

import com.example.news.data.network.NetworkMonitor
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

class BaseViewModelTest {
    // Tests that the isOnline state reflects the value provided by NetworkMonitor
    @Test
    fun `isOnline reflects network monitor state`() = runTest {
        val mockMonitor = mock(NetworkMonitor::class.java)
        `when`(mockMonitor.isOnline).thenReturn(flowOf(true))

        val testViewModel = object : BaseViewModel(mockMonitor) {}

        assertEquals(true, testViewModel.isOnline.first())
    }
}