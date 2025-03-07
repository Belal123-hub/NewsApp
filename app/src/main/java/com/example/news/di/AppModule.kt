package com.example.news.di

import android.util.Log
import com.example.news.data.remote.NewsApiService
import com.example.news.data.remote.NewsApiServiceImpl
import com.example.news.data.repository.NewsRepositoryImpl
import com.example.news.domain.repository.NewsRepository
import com.example.news.domain.usecase.GetTopHeadlinesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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
    // Provide Ktor HttpClient
    @Provides
    @Singleton
    fun provideKtorClient(): HttpClient {
        return HttpClient(Android) {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    prettyPrint = true
                })
            }
            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        Log.d("KtorLogger", message)
                    }
                }
                level = LogLevel.ALL
            }
        }
    }

    // Provide NewsApiService (Ktor-based)
    @Provides
    @Singleton
    fun provideNewsApiService(client: HttpClient): NewsApiService {
        return NewsApiServiceImpl(client)
    }

    // Provide NewsRepository
    @Provides
    @Singleton
    fun provideNewsRepository(apiService: NewsApiService): NewsRepository {
        return NewsRepositoryImpl(apiService)
    }

    // Provide GetTopHeadlinesUseCase
    @Provides
    @Singleton
    fun provideGetTopHeadlinesUseCase(repository: NewsRepository): GetTopHeadlinesUseCase {
        return GetTopHeadlinesUseCase(repository)
    }
}