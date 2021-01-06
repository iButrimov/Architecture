package com.example.architecturebase

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.architecturebase.ViewFragment.Companion.REQUEST_TIMEOUT_SECONDS
import com.example.architecturebase.adapter.MainAdapter
import com.example.architecturebase.databinding.FragmentViewBinding
import com.example.architecturebase.network.IPostApi
import com.example.architecturebase.network.model.Post
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ViewFragment : Fragment(R.layout.fragment_view), MvpContract.IView {

    //private val presenter: MvpContract.IPresenter = MvpPresenter(this)

    companion object {
        private const val REQUEST_TIMEOUT_SECONDS = 5L
    }

    private val binding by lazy {
        val bind = FragmentViewBinding.inflate(layoutInflater)
        bind
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .callTimeout(REQUEST_TIMEOUT_SECONDS, TimeUnit.SECONDS)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://jsonplaceholder.typicode.com")
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    private val mainAdapter = MainAdapter()
    private val postApi = retrofit.create(IPostApi::class.java)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //presenter.getPosts()

        binding.mainRV.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = mainAdapter
        }
        binding.listSRL.isRefreshing = true
        postApi.getPosts().enqueue(object : Callback<List<Post>> {
            override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
                if (response.isSuccessful) {
                    response.body()?.let { posts ->
                        // logic starts
                        val processedPosts = posts.filter {
                            !it.title.startsWith("H")
                        }.map {
                            it.copy(title = it.title + "appendix")
                        }.sortedBy {
                            it.title
                        }.subList(0, posts.size - 3)
                        // logic ends
                        mainAdapter.items = processedPosts
                        binding.listSRL.isRefreshing = false
                    }
                }
            }

            override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                Toast.makeText(requireContext(), t.message, Toast.LENGTH_SHORT).show()
                t.printStackTrace()
                binding.listSRL.isRefreshing = false
            }
        })

        binding.listSRL.setOnRefreshListener {
            mainAdapter.items = emptyList()

            postApi.getPosts().enqueue(object : Callback<List<Post>> {
                override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
                    if (response.isSuccessful) {
                        response.body()?.let { posts ->
                            // logic starts
                            val processedPosts = posts.filter {
                                !it.title.startsWith("H")
                            }.map {
                                it.copy(title = it.title + "appendix")
                            }.sortedBy {
                                it.title
                            }.subList(0, posts.size - 3)
                            // logic ends
                            mainAdapter.items = processedPosts
                            binding.listSRL.isRefreshing = false
                        }
                    }
                }

                override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                    Toast.makeText(requireContext(), t.message, Toast.LENGTH_SHORT).show()
                    t.printStackTrace()
                    binding.listSRL.isRefreshing = false
                }
            })
        }
    }
}