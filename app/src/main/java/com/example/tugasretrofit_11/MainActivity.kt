// MainActivity.kt
package com.example.tugasretrofit_11

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tugasretrofit_11.databinding.ActivityMainBinding
import com.example.tugasretrofit_11.retrofit.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val tag: String = "MainActivity"

    private lateinit var movieAdapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Avengers"
        setupRecyclerView()
        getDataFromApi()
    }

    private fun setupRecyclerView() {
        movieAdapter = MovieAdapter(arrayListOf(), object : MovieAdapter.OnAdapterListener {
            override fun onClick(result: MainModel.Result, context: Context) {
                startActivity(
                    Intent(context, DetailActivity::class.java)
                        .putExtra("intent_title", result.title)
                        .putExtra("intent_image", result.image)
                )
            }
        })
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = movieAdapter
        }
    }

    private fun getDataFromApi() {
        showLoading(true)
        ApiService.endpoint.data()
            .enqueue(object : Callback<MainModel> {
                override fun onFailure(call: Call<MainModel>, t: Throwable) {
                    printLog(t.toString())
                    showLoading(false)
                }

                override fun onResponse(
                    call: Call<MainModel>,
                    response: Response<MainModel>
                ) {
                    showLoading(false)
                    if (response.isSuccessful) {
                        showResult(response.body()!!)
                    } else {
                        printLog("Respon tidak berhasil: ${response.code()}")
                    }
                }
            })
    }

    private fun printLog(message: String) {
        Log.d(tag, message)
    }

    private fun showLoading(loading: Boolean) {
        when (loading) {
            true -> binding.progressBar.visibility = View.VISIBLE
            false -> binding.progressBar.visibility = View.GONE
        }
    }

    private fun showResult(results: MainModel) {
        for (result in results.result) printLog("title: ${result.title}")
        movieAdapter.setData(results.result)
    }
}
