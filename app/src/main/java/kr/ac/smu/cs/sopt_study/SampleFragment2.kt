package kr.ac.smu.cs.sopt_study

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_sample_fragment2.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [SampleFragment2.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [SampleFragment2.newInstance] factory method to
 * create an instance of this fragment.
 */
class SampleFragment2 : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var sampleAdapter: SampleAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_sample_fragment2, container, false)
        val samplercv=view.findViewById<RecyclerView>(R.id.sample_rcv2)   //fragment에서는 findviewById로 가져와야함
        sampleAdapter = SampleAdapter(view.context)

        samplercv.adapter = sampleAdapter
        samplercv.layoutManager = GridLayoutManager(view.context,2)
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
                val adapter = samplercv.adapter as SampleAdapter
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

                val adapter = samplercv.adapter as SampleAdapter
                adapter.remove(position)


            }


        }
        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
        itemTouchHelper.attachToRecyclerView(samplercv)
        return view




    }

}
