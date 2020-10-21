package kr.ac.smu.cs.sopt_study

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_sample_touch.*

class SampleTouchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sample_touch)

        var title = getIntent().getStringExtra("title")
        var subTitle=getIntent().getStringExtra("subTitle")
        var image=getIntent().getIntExtra("image",0)
        var day=getIntent().getStringExtra("day")
        var text=getIntent().getStringExtra("text")

        touch_day.text=day
        touch_title.text=title
        touch_subtitle.text=subTitle
        touch_text.text=text
        touch_image.setImageResource(image)
    }
}
