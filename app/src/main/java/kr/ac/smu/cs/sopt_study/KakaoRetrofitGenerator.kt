package kr.ac.smu.cs.sopt_study

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object KakaoRetrofitGenerator {
    val builder = OkHttpClient.Builder()
    init {
        val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }       // Retrofit 에서 통신 과정의 로그를 확인하기 위함. 로그의 level 을 지정
        builder.addInterceptor(httpLoggingInterceptor)

    }
    val okHttpClient = builder
        .build()

    private val retrofit =
        Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("https://dapi.kakao.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    fun create(): KaKaoRetrofitService = retrofit.create(KaKaoRetrofitService::class.java)
}