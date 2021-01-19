package com.example.architecturebase.domain.uscases

import retrofit2.Call
import com.example.architecturebase.domain.entities.Post
import com.example.architecturebase.domain.repository.IRepository

class NewsUseCase (private val newRepository: IRepository) {

    fun execute(): Call<List<Post>> {
        return newRepository.loadPosts()
    }
}