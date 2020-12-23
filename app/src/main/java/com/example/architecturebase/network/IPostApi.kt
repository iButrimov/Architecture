package com.example.architecturebase.network

import com.example.architecturebase.network.model.Post
import retrofit2.Call
import retrofit2.http.GET

interface IPostApi {
    @GET("/posts")
    fun getPosts(): Call<List<Post>>
}
