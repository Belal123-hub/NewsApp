package com.example.news.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Article::class,HistoryArticleEntity::class],
    version = 2
)
abstract class NewsDatabase: RoomDatabase() {
    abstract fun articleDao(): ArticleDao
    abstract fun historyDao(): HistoryDao
}
