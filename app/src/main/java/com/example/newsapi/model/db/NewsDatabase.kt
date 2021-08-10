package com.example.newsapi.model.db

import android.content.Context
import androidx.room.*
import com.example.newsapi.model.entity.Article
import com.example.newsapi.model.entity.ArticleDao
import com.example.newsapi.model.entity.SaveNews
import com.example.newsapi.model.entity.SaveNewsDao

@Database(
    entities = [
        Article::class,
        SaveNews::class
    ],
    version = 2
)
abstract class NewsDatabase : RoomDatabase() {

    abstract fun artileDao(): ArticleDao
    abstract fun saveNewsDao(): SaveNewsDao

    companion object {

        @Volatile
        private var instance: NewsDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            NewsDatabase::class.java,
            "news_db"
        ).fallbackToDestructiveMigration().build()

    }

}