package com.example.architecturebase.network.usecases

import com.example.architecturebase.network.model.Post

class NewsUseCase {

    fun loadNewsUseCase (posts: List<Post>) : List<Post> {
        return posts.filter {
            !it.title.startsWith("H")
        }.map {
            it.copy(title = it.title + "appendix")
        }.sortedBy {
            it.title
        }.subList(0, posts.size - 3)
    }
}