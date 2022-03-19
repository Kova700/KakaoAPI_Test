package com.example.testkakaobookapi

import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.util.Log
import androidx.annotation.RequiresApi
import com.bumptech.glide.Glide
import com.example.testkakaobookapi.api.KakaoBookApiKey.Companion.API_KEY1
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Response
import java.security.MessageDigest

class MainActivity : AppCompatActivity() {

    companion object{
        const val TAG: String = "로그"
    }
    var Books : List<Document>? = null

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //해시 키 출력
        try {
            val info = packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES)
            for (signature in info.signatures) {
                val md: MessageDigest = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                val hashKey = String(Base64.encode(md.digest(), 0))
                Log.e(TAG, "해시키 : $hashKey")
            }
        } catch (e: Exception) {
            Log.e(TAG, "해시키를 찾을 수 없습니다 : $e")
        }

        btn_test.setOnClickListener {
            val searchtext = et_test.text.toString()
            if (searchtext != ""){
                //검색 시작
                runBlocking {
                    launch {
                        val kakaoApi = RetrofitInstance.api
                        kakaoApi.searchBook(API_KEY1,searchtext).enqueue(object : retrofit2.Callback<BookResponse> {
                            override fun onResponse(
                                call: Call<BookResponse>,
                                response: Response<BookResponse>
                            ) {
                                Books = response.body()?.documents
                                Log.d(TAG, "MainActivity: - Books : ${Books}() - called")
                                Log.d(TAG, "MainActivity: onResponse() - called")
                                Log.d(TAG, "MainActivity: response.raw() - ${response.raw()}")
                                Log.d(TAG, "MainActivity: response.body() - ${response.body()}")

                                val book : Document? = Books?.get(0)
                                Log.d(TAG, "MainActivity: -Book : ${book} - called")

                                /*값 UI 작업*/
                                tv1_test.text="책 이름 : "
                                tv2_test.text="저자 명 : "
                                tv3_test.text="출판사  : "
                                tv4_test.text="ISBN   : "
                                tv1_test.append(book?.title)
                                tv2_test.append(book?.authors?.get(0))
                                tv3_test.append(book?.publisher)
                                tv4_test.append(book?.isbn)

                                Glide.with(this@MainActivity)
                                    .load(book?.thumbnail)
                                    .into(iv1_test)

                            }

                            override fun onFailure(call: Call<BookResponse>, t: Throwable) {
                                Log.d(TAG, "MainActivity: onFailure() - called")
                            }

                        })
                    }
                }
            }

        }

    }

}

