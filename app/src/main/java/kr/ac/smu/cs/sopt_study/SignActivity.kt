package kr.ac.smu.cs.sopt_study

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_sign.*
import kr.ac.smu.cs.sopt_study.Request.RequestSignupBody
import kr.ac.smu.cs.sopt_study.Response.ResponseSignupBody
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class SignActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign)

        sign_button.setOnClickListener {
            if(sign_id.text.toString()==""||sign_pw.text.toString()==""||name_edit.text.toString()==""){
                Toast.makeText(this,"빈칸이 있습니다", Toast.LENGTH_SHORT).show()
            }else {
                //Toast.makeText(this,"회원가입이 완료되었습니다",Toast.LENGTH_SHORT).show()
                PostServer(sign_id.text.toString(),sign_pw.text.toString(),name_edit.text.toString())
                val intent = Intent()
                intent.putExtra("id", sign_id.text.toString())
                intent.putExtra("pw", sign_pw.text.toString())
                setResult(Activity.RESULT_OK, intent)
                finish()
            }
        }
    }

    private fun PostServer(email: String, password: String,userName:String) {
        val postRequest=RequestSignupBody(email,password,userName)
        val call =
            RetrofitGenerator.create().postSignup(postRequest)
        call.enqueue(object : Callback<ResponseSignupBody> {
            override fun onResponse(call: Call<ResponseSignupBody>, response: Response<ResponseSignupBody>) {
                if (response.isSuccessful == false) {
                    if (response.code() == 400) {
                        //JSONObject를 활용해서 에러 객체를 받아올 수 있다
                        val ob=JSONObject(response.errorBody()?.string())

                        Toast.makeText(applicationContext,ob.getString("message"),Toast.LENGTH_SHORT).show()
                    } else {

                    }
                } else {
                    Toast.makeText(applicationContext,"회원가입에 성공했습니다",Toast.LENGTH_SHORT).show()
                }

            }

            override fun onFailure(call: Call<ResponseSignupBody>, t: Throwable) {
                Toast.makeText(applicationContext,"회원가입에 실패했습니다 ",Toast.LENGTH_SHORT).show()
            }
        })
    }


}
