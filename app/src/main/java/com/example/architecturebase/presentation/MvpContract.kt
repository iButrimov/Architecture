package com.example.architecturebase.presentation

import com.example.architecturebase.domain.entities.Post
import java.lang.Exception

interface MvpContract {

    interface IView {
        fun showPosts(posts: List<Post>)
        fun showErrors(e: Exception)
    }

    interface IPresenter {
        fun getPost()
    }
}