package kr.ac.smu.cs.sopt_study

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.activity_fragment.*
import kotlin.properties.Delegates

class FragmentActivity : AppCompatActivity() {
    var code=1
    private lateinit var viewpagerAdapter:ViewPagerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment)

        viewpagerAdapter=ViewPagerAdapter(supportFragmentManager)
        viewpagerAdapter.fragments=listOf(SampleFragment(),SampleFragment2(),SampleFragment3())
        sampleViewPager.adapter=viewpagerAdapter
        //viewpager 스와이프 할때 네비게이션 조절
        sampleViewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {}
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {}
            // ViewPager의 페이지 중 하나가 선택된 경우
            override fun onPageSelected(position: Int) {
                sample_bottom_navi.menu.getItem(position).isChecked = true
            }
        })
//바텀 네비게이션 세팅
        sample_bottom_navi.setOnNavigationItemSelectedListener {
            var index by Delegates.notNull<Int>()
            when(it.itemId){
                R.id.bottom_image1 -> index = 0
                R.id.bottom_image2 -> index = 1
                R.id.bottom_image3 -> index = 2
            }
            sampleViewPager.currentItem = index
            true
        }

        /*sample_tab.setupWithViewPager(sampleViewPager)
        sample_tab.apply {
            getTabAt(0)?.text = "첫 번째"
            getTabAt(1)?.text = "두 번째"
            getTabAt(2)?.text = "세 번째"
        }*/


        /*val fragment1=SampleFragment()
        val fragment2=SampleFragment2()
        //fragmentManager 생성 supportFragmentManager로 매니저 생성, add를 통해 fragment 추가 commit으로 작업수행
        supportFragmentManager.beginTransaction().add(R.id.fragment_container,fragment1).commit()

        changeButton.setOnClickListener {
            val transaction=supportFragmentManager.beginTransaction()

            when(code){
                1->{
                    transaction.replace(R.id.fragment_container,fragment2)
                    code=2
                }
                2->{
                    transaction.replace(R.id.fragment_container,fragment1)
                    code=1
                }
            }
            transaction.commit()
        }*/

    }
}
