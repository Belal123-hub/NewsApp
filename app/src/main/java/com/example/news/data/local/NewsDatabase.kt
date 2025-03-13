package com.example.news.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Article::class], version = 1)
abstract class NewsDatabase: RoomDatabase() {
    abstract fun articleDao(): ArticleDao
}