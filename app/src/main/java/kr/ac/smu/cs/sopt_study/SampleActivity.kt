package kr.ac.smu.cs.sopt_study

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_sample.*

class SampleActivity : AppCompatActivity() {
    private lateinit var sampleAdapter:SampleAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sample)

        sampleAdapter=SampleAdapter(this)

        sample_rcv.adapter=sampleAdapter
        sample_rcv.layoutManager=LinearLayoutManager(this)
        sampleAdapter.data= mutableListOf(
            SampleData("박건우","두산베어스",R.drawable.img1,"2020년 10월 18일","두산의 선수이다"),
            SampleData("김광현","SK와이번스",R.drawable.img2,"2020년 10월 18일","SK의 선수이다"),
            SampleData("권혁","한화이글스",R.drawable.img3,"2020년 10월 18일","한화의 선수이다"),
            SampleData("이정후","키움히어로즈",R.drawable.img4,"2020년 10월 18일","키움의 선수이다"),
            SampleData("양현종","기아타이거즈",R.drawable.img5,"2020년 10월 18일","기아의 선수이다"),
            SampleData("심수창","한화이글스",R.drawable.img6,"2020년 10월 18일","한화의 선수이다"))

        sampleAdapter.notifyDataSetChanged()


    }
}
