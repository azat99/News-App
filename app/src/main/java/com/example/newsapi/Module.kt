package com.example.newsapi

import com.example.newsapi.model.api.ApiService
import com.example.newsapi.model.db.NewsDatabase
import com.example.newsapi.repositories.Repository
import com.example.newsapi.repositories.RepositoryImpl
import com.example.newsapi.viewModel.DetailsViewModel
import com.example.newsapi.viewModel.NewsViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule= module {
    single {
        RepositoryImpl(
            NewsDatabase(context = androidContext()).artileDao(),
            NewsDatabase(context = androidContext()).saveNewsDao(),
            ApiService()
        ) as Repository
    }

    viewModel { NewsViewModel(get()) }
    viewModel { DetailsViewModel(get()) }
}