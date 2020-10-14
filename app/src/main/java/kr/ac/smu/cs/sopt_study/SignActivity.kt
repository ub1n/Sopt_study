package kr.ac.smu.cs.sopt_study

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_sign.*

class SignActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign)

        sign_button.setOnClickListener {
            if(sign_id.text.toString()==""||sign_pw.text.toString()==""||name_edit.text.toString()==""){
                Toast.makeText(this,"빈칸이 있습니다", Toast.LENGTH_SHORT).show()
            }else {
                Toast.makeText(this,"회원가입이 완료되었습니다",Toast.LENGTH_SHORT).show()
                val intent = Intent()
                intent.putExtra("id", sign_id.text.toString())
                intent.putExtra("pw", sign_pw.text.toString())
                setResult(Activity.RESULT_OK, intent)
                finish()
            }
        }
    }
}
