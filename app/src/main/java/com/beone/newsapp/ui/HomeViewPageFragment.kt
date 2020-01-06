package com.beone.newsapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.beone.newsapp.R
import com.beone.newsapp.adapter.*
import com.beone.newsapp.databinding.FragmentViewPagerBinding
import com.google.android.material.tabs.TabLayoutMediator


class HomeViewPageFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentViewPagerBinding.inflate(inflater, container, false)
        initToolbar(binding)
        initTabLayoutMediator(binding)
        return binding.root

    }

    private fun initTabLayoutMediator(binding: FragmentViewPagerBinding) {
        val tabLayout = binding.tabs
        val viewPager = binding.viewPager
        viewPager.adapter = NewsPagerAdapter(this)
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = getTabTitle(position)
        }.attach()
    }

    private fun initToolbar(binding: FragmentViewPagerBinding) {
        val activity = (requireNotNull(value = this.activity) as MainActivity)
        val appBarConfiguration =
            AppBarConfiguration(findNavController().graph, activity.getDrawer())
        val viewPagerToolbar = binding.viewPagerToolbar
        with(viewPagerToolbar) {
            setupWithNavController(findNavController(), appBarConfiguration)
            setNavigationIcon(R.drawable.ic_nav_menu)
        }
    }

    private fun getTabTitle(position: Int): String? {
        return when (position) {
            TOP_NEWS_PAGE_INDEX -> getString(R.string.news_title)
            TOP_BUSINESS_PAGE_INDEX -> getString(R.string.business_news_title)
            TOP_ENTERTAINMENT_PAGE_INDEX -> getString(R.string.entertainment_news_title)
            TOP_HEALTH_PAGE_INDEX -> getString(R.string.health)
            TOP_SCIENCE_PAGE_INDEX -> getString(R.string.science)
            TOP_SPORTS_PAGE_INDEX -> getString(R.string.sports)
            TOP_TECH_PAGE_INDEX -> getString(R.string.tech)
            else -> null
        }
    }
}