package com.example.news

import android.app.Application
import androidx.test.core.app.ApplicationProvider
import junit.framework.TestCase.assertTrue
import org.junit.Test

class NewsApplicationSimpleTest {
    // Verifies that the application class is correctly initialized as NewsApplication
    @Test
    fun verifyApplicationClass() {
        val appContext = ApplicationProvider.getApplicationContext<Application>()
        assertTrue(appContext is NewsApplication)
    }
}