package kr.ac.smu.cs.sopt_study

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [SampleFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [SampleFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SampleFragment : Fragment() {
    // TODO: Rename and change types of parameters

    private lateinit var viewpagerAdapter:ViewPagerAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_sample, container, false)
        val viewPager=view.findViewById<ViewPager>(R.id.first_viewPager)
        val taplayout=view.findViewById<TabLayout>(R.id.first_tab)
        viewpagerAdapter=ViewPagerAdapter(childFragmentManager)
        viewpagerAdapter.fragments=listOf(InfoFragment(),OtherFragment())
        viewPager.adapter=viewpagerAdapter

        taplayout.setupWithViewPager(viewPager)
        taplayout.apply{
            getTabAt(0)?.text="INFO"
            getTabAt(1)?.text="OTHER"
        }




        return view
    }



}
