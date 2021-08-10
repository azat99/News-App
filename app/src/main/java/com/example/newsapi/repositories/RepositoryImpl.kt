package com.example.newsapi.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.newsapi.model.api.ApiService
import com.example.newsapi.model.entity.Article
import com.example.newsapi.model.entity.ArticleDao
import com.example.newsapi.model.entity.SaveNews
import com.example.newsapi.model.entity.SaveNewsDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class RepositoryImpl(
    private val articleDao: ArticleDao,
    private val saveNewsDao: SaveNewsDao,
    private val apiService: ApiService
) : Repository {
    override suspend fun getNewsListFromNewsAPI(pageSize: Int) {
        val category = "bitcoin"
        val pageSize = pageSize
        val apiKey = "e65ee0938a2a43ebb15923b48faed18d"

        withContext(Dispatchers.IO) {
            try {
                val result = apiService.getNewsList(category, pageSize, apiKey).await()
                articleDao.deleteNewsListFromDB()
                articleDao.insertNewsListToDB(result.articles)
            } catch (e: Exception) {
                Log.i("exeption_error", e.printStackTrace().toString())
            }
        }

    }

    override fun getNewsListFromDB(): LiveData<List<Article>> {
        return articleDao.getAllNewsListFromDB()
    }

    override fun saveNewsToDB(likedData: SaveNews) {
        CoroutineScope(Dispatchers.IO).launch {
            saveNewsDao.insertSaveNewsListToDB(likedData)
        }
    }

    override fun getSavedMovieFromDB(): LiveData<List<SaveNews>> {
        return saveNewsDao.getAllSaveNewsListFromDB()
    }
}