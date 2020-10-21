package kr.ac.smu.cs.sopt_study

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SampleViewHolder (itemview: View) : RecyclerView.ViewHolder(itemview){

    private val title: TextView =itemview.findViewById(R.id.sample_text2)
    private val subtitle:TextView=itemview.findViewById(R.id.sample_text1)
    private val image: ImageView =itemview.findViewById(R.id.sample_image)

    fun onBind(data:SampleData){
        title.text=data.titile
        subtitle.text=data.subTitle
        image.setImageResource(data.image)
    }

}