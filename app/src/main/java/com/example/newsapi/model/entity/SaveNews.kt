package com.example.newsapi.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class SaveNews(
    @SerializedName("id")
    val id: Int,
    @SerializedName("author")
    val author: String?,
    @SerializedName("title")
    val title: String,
    @SerializedName("urlToImage")
    val urlToImage: String?,
    @SerializedName("content")
    val content: String?
){
    @PrimaryKey(autoGenerate = true)
    var s_id:Int = id
}
