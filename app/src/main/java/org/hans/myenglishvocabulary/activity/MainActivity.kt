package org.hans.myenglishvocabulary.activity

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import org.hans.myenglishvocabulary.R
import org.hans.myenglishvocabulary.adapter.ViewPager2Adapter
import org.hans.myenglishvocabulary.databinding.ActivityMainBinding
import org.hans.myenglishvocabulary.fragment.FabDialogAddFragment
import org.hans.myenglishvocabulary.stuff.ZoomOutPageTransformer

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    val tabsTextArray = arrayOf("List", "Study")
    val tabsIconArray = arrayOf(R.drawable.ic_list_white, R.drawable.ic_study_white)
    var mBackWait: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 액션바에 툴바 설정
        setSupportActionBar(binding.toolbar)

        // 탭스 설정
        binding.tabs.apply {
            setTabTextColors(Color.WHITE, Color.RED)
            setSelectedTabIndicatorColor(Color.RED)
            addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    if (selectedTabPosition == 0) {
                        binding.fab.show()
                    } else {
                        binding.fab.hide()
                    }
                }
                override fun onTabUnselected(tab: TabLayout.Tab?) {}
                override fun onTabReselected(tab: TabLayout.Tab?) {}
            })
        }

        // 뷰페이저2 설정
        binding.menuViewPager2.apply {
            adapter = ViewPager2Adapter(context as FragmentActivity)
            setPageTransformer(ZoomOutPageTransformer())
        }

        // 뷰페이저2와 탭스 연결
        TabLayoutMediator(binding.tabs, binding.menuViewPager2) { tab: TabLayout.Tab, i: Int ->
            tab.text = tabsTextArray[i]
            tab.setIcon(tabsIconArray[i])
        }.attach()

        // 플로팅 액션 버튼 설정(다이얼로그 설정)
        binding.fab.setOnClickListener {
            val dialogAddFragment = FabDialogAddFragment()

            dialogAddFragment.show(supportFragmentManager, "FabDialogAddFragment")
        }
    }

    // 뒤로가기 버튼
    override fun onBackPressed() {
        if (binding.menuViewPager2.currentItem == 0) {
            if(System.currentTimeMillis() - mBackWait >= 2000) {
                mBackWait = System.currentTimeMillis()
                Toast.makeText(applicationContext,"한번 더 누르면 종료됩니다.",Toast.LENGTH_SHORT).show()
            } else {
                super.onBackPressed()
            }
        } else {
            binding.menuViewPager2.currentItem = binding.menuViewPager2.currentItem - 1
        }
    }
}