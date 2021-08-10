package com.example.newsapi.model.entity

import com.google.gson.annotations.SerializedName


data class Source(
    @SerializedName("id")
    val id: String?,
    @SerializedName("name")
    val name: String
)