package com.example.architecturebase

import android.widget.Toast
import com.example.architecturebase.network.model.Post
import com.example.architecturebase.network.usecases.NewsUseCase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MvpPresenter(private val viewFragment: ViewFragment) : MvpContract.IPresenter {

    private val repository: RemoteRepository = RemoteRepository()
    private val newsUseCase = NewsUseCase()

    override fun getPost() {
        repository.postApi.getPosts().enqueue(object : Callback<List<Post>> {
            override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
                if (response.isSuccessful) {
                    response.body()?.let { posts ->
                        viewFragment.mainAdapter.items = newsUseCase.loadNewsUseCase(posts)
                        viewFragment.binding.listSRL.isRefreshing = false
                    }
                }
            }

            override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                Toast.makeText(viewFragment.context, t.message, Toast.LENGTH_SHORT).show()
                t.printStackTrace()
                viewFragment.binding.listSRL.isRefreshing = false
            }
        })
    }
}