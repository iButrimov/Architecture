package com.example.architecturebase.presentation

import com.example.architecturebase.domain.entities.Post
import com.example.architecturebase.data.RemoteRepository
import com.example.architecturebase.domain.uscases.NewsUseCase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class MvpPresenter(private val viewFragment: MvpContract.IView) : MvpContract.IPresenter {

    private val getPostsUseCase = NewsUseCase(RemoteRepository())

    override fun getPost() {
        viewFragment.showPosts(emptyList())

        getPostsUseCase.execute().enqueue(object : Callback<List<Post>> {
            override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
                if (response.isSuccessful) {
                    response.body()?.let { posts ->
                        //logic starts
                        val processedPosts =  posts.filter {
                            !it.title.startsWith("H")
                        }.map {
                            it.copy(title = it.title + "appendix")
                        }.sortedBy {
                            it.title
                        }.subList(0, posts.size - 3)
                        //logic ends
                        viewFragment.showPosts(processedPosts)
                    }
                }
            }

            override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                viewFragment.showErrors(t as Exception)
                t.printStackTrace()
            }
        })
    }
}