package com.example.testkakaobookapi.api

import com.example.testkakaobookapi.BookResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface BookService {

    //마지막에 완성되는 url
    // https://dapi.kakao.com/v3/search/book?target=title

    @GET(" v3/search/book")
    /*suspend*/ fun searchBook(
        @Header("Authorization") apiKey: String,
        @Query("query") query: String
    ) : Call<BookResponse>

}
