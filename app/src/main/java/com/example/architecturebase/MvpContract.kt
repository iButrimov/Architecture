package com.example.architecturebase

interface MvpContract {

    interface IView {

    }

    interface IPresenter {
        fun getPosts()
    }

}