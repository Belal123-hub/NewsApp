package com.example.news.di

import com.example.news.data.util.ShareUtils
import android.content.Context
import androidx.room.Room
import com.example.news.data.local.NewsDatabase
import com.example.news.data.network.AndroidNetworkMonitor
import com.example.news.data.network.NetworkMonitor
import com.example.news.data.remote.NewsApiService
import com.example.news.data.remote.NewsApiServiceImpl
import com.example.news.data.remote.createKtorClient
import com.example.news.data.repository.NewsRepositoryImpl
import com.example.news.domain.repository.NewsRepository
import com.example.news.domain.usecase.GetHistoryUseCase
import com.example.news.domain.usecase.GetTopHeadlinesUseCase
import com.example.news.domain.usecase.SaveToHistoryUseCase
import com.example.news.domain.usecase.ShareArticleUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideHttpClient(): HttpClient {
        return createKtorClient()
    }

    // Provide NewsApiService (Ktor-based)
    @Provides
    @Singleton
    fun provideNewsApiService(client: HttpClient): NewsApiService {
        return NewsApiServiceImpl(client)
    }

    // Provide NewsDatabase (Room)
    @Provides
    @Singleton
    fun provideNewsDatabase(@ApplicationContext context: Context): NewsDatabase {
        return Room.databaseBuilder(context, NewsDatabase::class.java, "news.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    // Provide NewsRepository
    @Provides
    @Singleton
    fun provideNewsRepository(
        apiService: NewsApiService,
        newsDatabase: NewsDatabase,
        shareUils: ShareUtils
    ): NewsRepository {
        return NewsRepositoryImpl(apiService, newsDatabase, shareUils)
    }

    // Provide GetTopHeadlinesUseCase
    @Provides
    @Singleton
    fun provideGetTopHeadlinesUseCase(repository: NewsRepository): GetTopHeadlinesUseCase {
        return GetTopHeadlinesUseCase(repository)
    }
    @Provides
    @Singleton
    fun provideSaveToHistoryUseCase(repository: NewsRepository): SaveToHistoryUseCase {
        return SaveToHistoryUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetHistoryUseCase(repository: NewsRepository): GetHistoryUseCase {
        return GetHistoryUseCase(repository)
    }

    @Provides
    @Singleton
    fun ProvideNetworkMonitor(@ApplicationContext context: Context):NetworkMonitor{
        return AndroidNetworkMonitor(context)
    }

    @Provides
    @Singleton
    fun provideShareUtils(@ApplicationContext context: Context): ShareUtils {
        return ShareUtils(context)
    }

    @Provides
    @Singleton
    fun provideShareArticleUseCase(repository: NewsRepository): ShareArticleUseCase {
        return ShareArticleUseCase(repository)
    }

}
