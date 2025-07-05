package com.example.news.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface HistoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE )
    suspend fun insert(article: HistoryArticleEntity)
    @Query("SELECT * FROM history ORDER BY accessedAt DESC")
    fun getAllHistory(): PagingSource<Int, HistoryArticleEntity>

    @Query("DELETE FROM history WHERE url = :url")
    suspend fun deleteByUrl(url: String)
}