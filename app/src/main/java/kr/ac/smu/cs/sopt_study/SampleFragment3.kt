package kr.ac.smu.cs.sopt_study

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.sample_fragment3.*
import kr.ac.smu.cs.sopt_study.Response.ResponseSearchBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [SampleFragment3.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [SampleFragment3.newInstance] factory method to
 * create an instance of this fragment.
 */
class SampleFragment3 : Fragment() {
    // TODO: Rename and change types of parameters

    lateinit var sAdapter:SearchAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =inflater.inflate(R.layout.sample_fragment3, container, false)
        val searchBtn:Button =view.findViewById<Button>(R.id.searchbutton)
        sAdapter= SearchAdapter(view.context)
        val r= Runnable {
            try{
                //goalList=goalDatabase?.goalDao?.getGoal()!!
                sAdapter= SearchAdapter(view.context)
                sAdapter.notifyDataSetChanged()

                searchRcv.adapter=sAdapter
                searchRcv.layoutManager=
                    LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL,false)
                searchRcv.setHasFixedSize(true)


            }catch (e:Exception){
                Log.d("tag", "Error - $e")
            }
        }

        val thread=Thread(r)
        thread.start()
        searchBtn.setOnClickListener {
            val query=searchEdit.text.toString()
            PostGetServer(query)




        }
        return view


    }

    private fun PostGetServer(query:String){
        //Retrofit 서버 연결
        val call=KakaoRetrofitGenerator.create().getSearch(query)
        //val call=RetrofitGenerator.create().registerPost(postRequest,"Token "+TokenTon.Token)
        call.enqueue(object : Callback<ResponseSearchBody>{
            override fun onResponse(call: Call<ResponseSearchBody>?, response: Response<ResponseSearchBody>) {
                //토큰 값 받아오기
                //Toast.makeText(this@AddGoalActivity,response.body()?.title.toString(),Toast.LENGTH_LONG).show()
                //TokenTon.set(response.body()?.token.toString())
                // )
                if(response?.isSuccessful==false){

                    Toast.makeText(activity!!.applicationContext,"${response?.code()}",Toast.LENGTH_LONG).show()
                }else {
                    Toast.makeText(activity!!.applicationContext,"${response?.body()?.documents?.get(0)!!.title}",Toast.LENGTH_LONG).show()
                    try {
                        sAdapter.setGoalListItems(response?.body()?.documents!!)
                    } catch (e: Exception) {
                        //    Toast.makeText(this@MainActivity,"$e",Toast.LENGTH_LONG).show()
                    }
                    if (response?.body() != null) {
                        //  Toast.makeText(this@MainActivity,response.body()!![0].title,Toast.LENGTH_LONG).show()
                        sAdapter.setGoalListItems(response?.body()?.documents!!)
                    }
                    //목표 갯수



                }
            }
            override fun onFailure(call: Call<ResponseSearchBody>, t: Throwable) {

            }
        })
    }

}
