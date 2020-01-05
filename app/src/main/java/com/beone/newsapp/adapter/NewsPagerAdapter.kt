package com.beone.newsapp.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.beone.newsapp.ui.BusinessFragment
import com.beone.newsapp.ui.NewsFragment

const val TOP_NEWS_PAGE_INDEX = 0
const val TOP_BUSINESS_PAGE_INDEX = 1

class NewsPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    private val tabFragmentsCreators: Map<Int, () -> Fragment> = mapOf(
        TOP_NEWS_PAGE_INDEX to { NewsFragment() },
        TOP_BUSINESS_PAGE_INDEX to { BusinessFragment() }

    )

    override fun getItemCount() = tabFragmentsCreators.size

    override fun createFragment(position: Int): Fragment {
        return tabFragmentsCreators[position]?.invoke() ?: throw IndexOutOfBoundsException()
    }
}