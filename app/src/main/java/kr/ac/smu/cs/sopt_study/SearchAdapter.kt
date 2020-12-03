package kr.ac.smu.cs.sopt_study

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.search_item.view.*
import kr.ac.smu.cs.sopt_study.Response.Documents
import kr.ac.smu.cs.sopt_study.Response.ResponseSearchBody
import org.w3c.dom.Text

class SearchAdapter(context : Context) : RecyclerView.Adapter<SearchAdapter.ViewHolder>() {
    var goalList : List<Documents> = listOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.search_item, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount() = goalList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        goalList[position].let{ item ->
            with(holder) {

                titleText.setText(item.title)
            }
        }





    }
    fun setGoalListItems(goalList: List<Documents>){
        this.goalList = goalList;
        notifyDataSetChanged()
    }
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleText: TextView =view.item_title
    }
}