package kr.ac.smu.cs.sopt_study

import kr.ac.smu.cs.sopt_study.Request.RequestSigninBody
import kr.ac.smu.cs.sopt_study.Request.RequestSignupBody
import kr.ac.smu.cs.sopt_study.Response.ResponseSigninBody
import kr.ac.smu.cs.sopt_study.Response.ResponseSignupBody
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface RetrofitService {
    @Headers("Content-Type: application/json")
    @POST("/users/signup")
    fun postSignup(@Body body : RequestSignupBody) : Call<ResponseSignupBody>

    @Headers("Content-Type: application/json")
    @POST("/users/signin")
    fun postSignin(@Body body : RequestSigninBody) : Call<ResponseSigninBody>


}