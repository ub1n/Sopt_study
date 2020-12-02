package kr.ac.smu.cs.sopt_study

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kr.ac.smu.cs.sopt_study.Request.RequestSigninBody
import kr.ac.smu.cs.sopt_study.Request.RequestSignupBody
import kr.ac.smu.cs.sopt_study.Response.ResponseSigninBody
import kr.ac.smu.cs.sopt_study.Response.ResponseSignupBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    var success:Boolean=false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        login_check.setChecked(App.prefs.myCheckbox)
        id_check.setChecked(App.prefs.myCheckId)
        pw_check.setChecked(App.prefs.myCheckPw)
        if(id_check.isChecked){  //체크되어있으면 이메일 바로 띄워줌
            id_edit.setText(App.prefs.loginId)
        }
        if(pw_check.isChecked){
            pw_edit.setText(App.prefs.loginPw)
        }

        if((login_check.isChecked==true)&&(App.prefs.loginId.length!=0)){ //자동로그인
            val id=App.prefs.loginId
            val pw=App.prefs.loginPw
            PostServer(id,pw)


        }
        login_check.setOnClickListener{
            App.prefs.myCheckbox=login_check.isChecked //체크박스 상태저장
        }
        id_check.setOnClickListener{
            App.prefs.myCheckId=id_check.isChecked   //이메일 체크박스 상태 저장
        }
        pw_check.setOnClickListener{
            App.prefs.myCheckPw=pw_check.isChecked  //비밀번호 체크박스 상태저장
        }
        login_button.setOnClickListener {
            App.prefs.loginId=id_edit.text.toString()  //email 상태 저장
            App.prefs.loginPw=pw_edit.text.toString() //pw 상태저장
            val id = id_edit.text.toString()
            val pw = pw_edit.text.toString()
            PostServer(id,pw)


        }

        reg_button.setOnClickListener {

            val intent = Intent(this, SignActivity::class.java)
            startActivityForResult(intent,100)

        }


    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode ==RESULT_OK) {
            when (requestCode) {
                100 -> {

                    id_edit.setText(data!!.getStringExtra("id").toString())
                    pw_edit.setText( data!!.getStringExtra("pw").toString())
                }
            }
        }
    }

    private fun PostServer(email: String, password: String) {
        val postRequest= RequestSigninBody(email,password)
        val call =
            RetrofitGenerator.create().postSignin(postRequest)
        call.enqueue(object : Callback<ResponseSigninBody> {
            override fun onResponse(call: Call<ResponseSigninBody>, response: Response<ResponseSigninBody>) {
                if (response.isSuccessful == false) {
                    val ob= JSONObject(response.errorBody()?.string())
                } else {
                    Toast.makeText(applicationContext,"로그인에 성공했습니다",Toast.LENGTH_SHORT).show()
                    success=true
                    val intent = Intent(applicationContext, FragmentActivity::class.java)
                    applicationContext.startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
                    finish()
                }
            }

            override fun onFailure(call: Call<ResponseSigninBody>, t: Throwable) {
                Toast.makeText(applicationContext,"로그인에 실패했습니다 ",Toast.LENGTH_SHORT).show()
            }
        })
    }

}
