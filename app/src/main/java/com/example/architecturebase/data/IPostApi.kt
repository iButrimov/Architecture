package com.example.architecturebase.data

import com.example.architecturebase.domain.entities.Post
import retrofit2.Call
import retrofit2.http.GET

interface IPostApi {
    @GET("/posts")
    fun getPosts(): Call<List<Post>>
}
