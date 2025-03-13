package com.example.news.di

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.example.news.data.local.NewsDatabase
import com.example.news.data.remote.NewsApiService
import com.example.news.data.remote.NewsApiServiceImpl
import com.example.news.data.remote.NewsRemoteMediator
import com.example.news.data.remote.createKtorClient
import com.example.news.data.repository.NewsRepositoryImpl
import com.example.news.domain.repository.NewsRepository
import com.example.news.domain.usecase.GetTopHeadlinesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import javax.inject.Singleton
import io.ktor.client.plugins.logging.*


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
        return Room.databaseBuilder(
            context,
            NewsDatabase::class.java,
            "news.db"
        ).build()
    }

    // Provide NewsRepository
    @Provides
    @Singleton
    fun provideNewsRepository(
        apiService: NewsApiService,
        newsDatabase: NewsDatabase
    ): NewsRepository {
        return NewsRepositoryImpl(apiService, newsDatabase)
    }

    // Provide GetTopHeadlinesUseCase
    @Provides
    @Singleton
    fun provideGetTopHeadlinesUseCase(repository: NewsRepository): GetTopHeadlinesUseCase {
        return GetTopHeadlinesUseCase(repository)
    }
}