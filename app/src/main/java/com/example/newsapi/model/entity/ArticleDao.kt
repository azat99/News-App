package com.example.newsapi.model.entity

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ArticleDao {

    @Query("select * from article")
    fun getAllNewsListFromDB():LiveData<List<Article>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNewsListToDB(articleList: List<Article>)

    @Query("delete from article")
    fun deleteNewsListFromDB()

}