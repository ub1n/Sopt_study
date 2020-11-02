package kr.ac.smu.cs.sopt_study

import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class ViewPagerAdapter(fm: FragmentManager):FragmentStatePagerAdapter(fm){
    var fragments=listOf<Fragment>()
    override fun getCount(): Int =fragments.size
    override fun getItem(position: Int): Fragment =fragments[position]

}