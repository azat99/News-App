package com.example.newsapi.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.newsapi.model.entity.Article
import com.example.newsapi.model.entity.SaveNews
import com.example.newsapi.repositories.Repository

class DetailsViewModel(private val repository: Repository) : ViewModel() {

    fun getDetailsInfo(): LiveData<List<Article>> = repository.getNewsListFromDB()

    fun saveNewsToDB(saveNews: SaveNews) = repository.saveNewsToDB(saveNews)

}
