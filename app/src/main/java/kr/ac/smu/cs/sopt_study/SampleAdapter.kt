package kr.ac.smu.cs.sopt_study

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class SampleAdapter (private val context: Context):RecyclerView.Adapter<SampleViewHolder>(){
    var data=mutableListOf<SampleData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SampleViewHolder {
        val view=LayoutInflater.from(context).inflate(R.layout.sample_item,parent,false)
        return SampleViewHolder(view)
    }

    override fun getItemCount(): Int =data.size

    override fun onBindViewHolder(holder: SampleViewHolder, position: Int) {
        holder.onBind(data[position])

        holder.itemView.setOnClickListener {view->
            var intent= Intent(view.context, SampleTouchActivity::class.java)
            intent.putExtra("title",data[position].title)
            intent.putExtra("subTitle",data[position].subTitle)
            intent.putExtra("image",data[position].image)
            intent.putExtra("day",data[position].day)
            intent.putExtra("text",data[position].text)

            view.context.startActivity(intent)
        }
    }
    fun remove(position:Int){
        data.removeAt(position)
        notifyItemRemoved(position)
    }
    fun onItemMoved(from:Int,to:Int){
        if(from==to){
            return
        }
        val fromItem=data.removeAt(from)
        data.add(to,fromItem)
        notifyItemMoved(from,to)
    }
}