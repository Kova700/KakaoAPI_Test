package com.example.testkakaobookapi

import com.example.testkakaobookapi.api.BookService
import com.example.testkakaobookapi.api.KakaoBookApiKey.Companion.DOMAIN
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//Ratrofit을 불러오는 싱글톤 object
object RetrofitInstance {
    private val retrofit = Retrofit.Builder()
            .baseUrl(DOMAIN)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    private val _api = retrofit.create(BookService::class.java)
    val api
        get() = _api
}