package com.example.tugasretrofit_11.retrofit

import com.example.tugasretrofit_11.MainModel
import retrofit2.Call
import retrofit2.http.GET

interface ApiEndPoint {

//    interface
    @GET("data.php  ")
    fun data(): Call<MainModel>
}