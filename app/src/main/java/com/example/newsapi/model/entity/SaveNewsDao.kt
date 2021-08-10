package com.example.newsapi.model.entity

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SaveNewsDao {

    @Query("select * from savenews")
    fun getAllSaveNewsListFromDB(): LiveData<List<SaveNews>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSaveNewsListToDB(articleList: SaveNews)

    @Query("delete from savenews")
    fun deleteSaveNewsListFromDB()
}