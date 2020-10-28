package kr.ac.smu.cs.sopt_study

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_sample.*

class SampleActivity : AppCompatActivity()  {
    private lateinit var sampleAdapter: SampleAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sample)

        sampleAdapter = SampleAdapter(this)

        sample_rcv.adapter = sampleAdapter
        sample_rcv.layoutManager = GridLayoutManager(this,2)
        sampleAdapter.data = mutableListOf(
            SampleData("박건우", "두산베어스", R.drawable.img1, "2020년 10월 18일", "두산의 선수이다"),
            SampleData("김광현", "SK와이번스", R.drawable.img2, "2020년 10월 18일", "SK의 선수이다"),
            SampleData("권혁", "한화이글스", R.drawable.img3, "2020년 10월 18일", "한화의 선수이다"),
            SampleData("이정후", "키움히어로즈", R.drawable.img4, "2020년 10월 18일", "키움의 선수이다"),
            SampleData("양현종", "기아타이거즈", R.drawable.img5, "2020년 10월 18일", "기아의 선수이다"),
            SampleData("심수창", "한화이글스", R.drawable.img6, "2020년 10월 18일", "한화의 선수이다")
        )

        sampleAdapter.notifyDataSetChanged()




        val simpleItemTouchCallback = object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                val adapter = sample_rcv.adapter as SampleAdapter
                adapter.onItemMoved(viewHolder!!.adapterPosition, target!!.adapterPosition)

                return true
            }

            override fun getMovementFlags(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ): Int {
                val dragFlags = ItemTouchHelper.DOWN or ItemTouchHelper.UP
                val swipeFlags = ItemTouchHelper.START or ItemTouchHelper.END
                return ItemTouchHelper.Callback.makeMovementFlags(dragFlags, swipeFlags)
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                // 삭제되는 아이템의 포지션을 가져온다
                val position = viewHolder.adapterPosition
                // 데이터의 해당 포지션을 삭제한다


                // 아답타에게 알린다

                val adapter = sample_rcv.adapter as SampleAdapter
                adapter.remove(position)


            }


        }
        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
        itemTouchHelper.attachToRecyclerView(sample_rcv)



    }




}
