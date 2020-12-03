package kr.ac.smu.cs.sopt_study

import kr.ac.smu.cs.sopt_study.Request.RequestSearchBody
import kr.ac.smu.cs.sopt_study.Request.RequestSigninBody
import kr.ac.smu.cs.sopt_study.Request.RequestSignupBody
import kr.ac.smu.cs.sopt_study.Response.ResponseSearchBody
import kr.ac.smu.cs.sopt_study.Response.ResponseSigninBody
import kr.ac.smu.cs.sopt_study.Response.ResponseSignupBody
import retrofit2.Call
import retrofit2.http.*

interface KaKaoRetrofitService {
    @Headers("Authorization: KakaoAK 06923f547440cd7014e8c066dc880462")
    @GET("/v2/search/web")
    fun getSearch(@Query ("query") query:String) : Call<ResponseSearchBody>






}