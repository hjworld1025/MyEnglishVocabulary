package org.hans.myenglishvocabulary.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import org.hans.myenglishvocabulary.fragment.ListFragment
import org.hans.myenglishvocabulary.fragment.StudyFragment

class ViewPager2Adapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
    private val listFragment = ListFragment()
    private val studyFragment = StudyFragment()
    private val fragList = arrayOf(listFragment, studyFragment)

    override fun getItemCount(): Int {
        return fragList.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragList[position]
    }
}