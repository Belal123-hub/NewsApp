package com.example.news.data.remote

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.news.data.local.Article
import com.example.news.data.local.NewsDatabase
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class NewsRemoteMediator(
    private val newsApiService: NewsApiService,
    private val newsDatabase: NewsDatabase,
    private val country: String
) : RemoteMediator<Int, Article>() {

    private val articleDao = newsDatabase.articleDao()
    private var currentPage = 1

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Article>
    ): MediatorResult {
        return try {
            Log.d("NewsRemoteMediator", "Load type: $loadType")

            val page = when (loadType) {
                LoadType.REFRESH -> {
                    Log.d("NewsRemoteMediator", "Refreshing data...")
                    currentPage = 1 // Reset to page 1 on refresh
                    currentPage
                }
                LoadType.PREPEND -> {
                    Log.d("NewsRemoteMediator", "Prepend not supported")
                    return MediatorResult.Success(endOfPaginationReached = true) // No prepend needed
                }
                LoadType.APPEND -> {
                    // Get the last item in the current list
                    val lastItem = state.lastItemOrNull()

                    // Log the last item
                    Log.d("NewsRemoteMediator", "Last item: ${lastItem?.title}")

                    // If the last item is null, we've reached the end of pagination
                    if (lastItem == null) {
                        Log.d("NewsRemoteMediator", "Last item is null, end of pagination reached")
                        return MediatorResult.Success(endOfPaginationReached = true)
                    }
                    // Increment the current page number
                    currentPage += 1
                    currentPage
                }
            }

            // Log the page number
            Log.d("NewsRemoteMediator", "Loading page: $page")

            // Fetch the next page of articles from the API
            val response = newsApiService.getTopHeadlines(country = country, page = page, pageSize = state.config.pageSize)
            Log.d("NewsRemoteMediator", "API response: $response")

            // Check if the API returned an error
            if (response.status != "ok") {
                Log.e("NewsRemoteMediator", "API error: ${response.status}")
                return MediatorResult.Error(Exception("API error: ${response.status}"))
            }
            val articles = response.articles?.mapNotNull { apiArticle ->
                if (apiArticle.url == null || apiArticle.title == null) {
                    Log.w("NewsRemoteMediator", "Skipping article with missing required fields: $apiArticle")
                    null
                } else {
                    Article(
                        url = apiArticle.url,
                        title = apiArticle.title,
                        description = apiArticle.description ?: "",
                        urlToImage = apiArticle.urlToImage ?: "",
                        publishedAt = apiArticle.publishedAt ?: "",
                        source = apiArticle.source?.name ?: ""
                    )
                }
            } ?: emptyList()

            Log.d("NewsRemoteMediator", "Fetched ${articles.size} articles")
            newsDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    Log.d("NewsRemoteMediator", "Clearing database on refresh")
                    articleDao.clearAll() // Clear the database on refresh
                }
                Log.d("NewsRemoteMediator", "Inserting ${articles.size} articles into the database")
                articleDao.insertAll(articles) // Insert new articles
            }
            val endOfPaginationReached = articles.isEmpty()
            Log.d("NewsRemoteMediator", "End of pagination reached: $endOfPaginationReached")

            // Return the result with the next page key
            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: IOException) {
            Log.e("NewsRemoteMediator", "IOException: ${e.message}", e)
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            Log.e("NewsRemoteMediator", "HttpException: ${e.message}", e)
            MediatorResult.Error(e)
        } catch (e: Exception) {
            Log.e("NewsRemoteMediator", "Unexpected error: ${e.message}", e)
            MediatorResult.Error(e)
        }
    }
}