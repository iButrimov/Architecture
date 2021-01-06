package com.example.architecturebase

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.architecturebase.adapter.MainAdapter
import com.example.architecturebase.databinding.ActivityMainBinding
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

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragment = ViewFragment()

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().apply {
                setReorderingAllowed(true)
                add(R.id.fragmentContainerView, fragment)
                commit()
            }
        }
    }
}