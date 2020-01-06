package com.beone.newsapp.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.beone.newsapp.ui.*

const val TOP_NEWS_PAGE_INDEX = 0
const val TOP_BUSINESS_PAGE_INDEX = 1
const val TOP_ENTERTAINMENT_PAGE_INDEX = 2
const val TOP_HEALTH_PAGE_INDEX = 3
const val TOP_SCIENCE_PAGE_INDEX = 4
const val TOP_SPORTS_PAGE_INDEX = 5
const val TOP_TECH_PAGE_INDEX = 6

class NewsPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    private val tabFragmentsCreators: Map<Int, () -> Fragment> = mapOf(
        TOP_NEWS_PAGE_INDEX to { NewsFragment() },
        TOP_BUSINESS_PAGE_INDEX to { BusinessFragment() },
        TOP_ENTERTAINMENT_PAGE_INDEX to { EntertainmentFragment() },
        TOP_HEALTH_PAGE_INDEX to { HealthFragment() },
        TOP_SCIENCE_PAGE_INDEX to { ScienceFragment() },
        TOP_SPORTS_PAGE_INDEX to { SportsFragment() },
        TOP_TECH_PAGE_INDEX to { TechnologyFragment() }

    )

    override fun getItemCount() = tabFragmentsCreators.size

    override fun createFragment(position: Int): Fragment {
        return tabFragmentsCreators[position]?.invoke() ?: throw IndexOutOfBoundsException()
    }
}