package com.example.newsapi.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapi.model.entity.Article
import com.example.newsapi.model.entity.SaveNews
import com.example.newsapi.repositories.Repository
import kotlinx.coroutines.launch

class NewsViewModel(private val repository: Repository) : ViewModel() {

    fun requestToNewsAPI(pageSize: Int) = viewModelScope.launch {
        repository.getNewsListFromNewsAPI(pageSize)
    }

    fun getNewsList(): LiveData<List<Article>> = repository.getNewsListFromDB()

    fun getSavedNews() = repository.getSavedMovieFromDB()
}
