package com.example.architecturebase.domain.entities

import com.google.gson.annotations.SerializedName

data class Post(
    @SerializedName("id")
    val id: Long,
    @SerializedName("title")
    val title: String,
    @SerializedName("body")
    val body: String
)