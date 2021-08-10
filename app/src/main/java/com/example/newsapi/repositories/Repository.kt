package com.example.newsapi.repositories

import androidx.lifecycle.LiveData
import com.example.newsapi.model.entity.Article
import com.example.newsapi.model.entity.SaveNews

interface Repository {

    suspend fun getNewsListFromNewsAPI(pageSize:Int)
    fun getNewsListFromDB():LiveData<List<Article>>

    fun saveNewsToDB(likedData: SaveNews)
    fun getSavedMovieFromDB(): LiveData<List<SaveNews>>
}