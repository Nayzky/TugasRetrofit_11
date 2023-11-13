package com.example.tugasretrofit_11

//mengelompokkan data dari api
class MainModel ( val result: ArrayList<Result>){
    data class Result (    val id:Int, val title:String, val image: String)

}