package com.beone.newsapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.beone.newsapp.R
import com.beone.newsapp.adapter.NewsListener
import com.beone.newsapp.adapter.TopNewsListAdapter
import com.beone.newsapp.databinding.FragmentSportsBinding
import com.beone.newsapp.domain.TopNews
import com.beone.newsapp.extensions.isNetworkAvailable
import com.beone.newsapp.network.ApiStatus
import com.beone.newsapp.viewmodel.SportsViewModel
import com.beone.newsapp.viewmodel.SportsViewModelFactory

class SportsFragment : Fragment() {

    private val sportsViewModel: SportsViewModel by lazy {
        val activity = requireNotNull(value = this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        ViewModelProviders.of(this, SportsViewModelFactory(activity.application))
            .get(SportsViewModel::class.java)
    }

    private lateinit var adapter: TopNewsListAdapter
    private lateinit var binding: FragmentSportsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSportsBinding.inflate(inflater)
        initBinding(binding)
        initSwipeRefreshLayout(binding)
        initListData()
        observeStatus()
        return binding.root
    }

    private fun observeStatus() {
        sportsViewModel.status.observe(viewLifecycleOwner, Observer { status ->
            @Suppress("WHEN_ENUM_CAN_BE_NULL_IN_JAVA")
            when (status) {
                ApiStatus.DONE -> {
                    binding.swipeToRefreshSports.isRefreshing = false
                    binding.txtConnectionSports.visibility = View.GONE
                }
                ApiStatus.ERROR -> {
                    when (requireNotNull(value = this.activity).isNetworkAvailable()) {
                        true -> binding.txtConnectionSports.text = "Server not responding"
                        false -> binding.txtConnectionSports.text = "No internet connection"
                    }
                    binding.swipeToRefreshSports.isRefreshing = false
                    binding.txtConnectionSports.visibility = View.VISIBLE
                    binding.txtConnectionSports.postDelayed({
                        binding.txtConnectionSports.visibility = View.GONE
                    }, 3000)
                }
            }
        })
    }

    private fun initBinding(binding: FragmentSportsBinding) {
        adapter = TopNewsListAdapter(NewsListener { navigateToNews(it) })
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            sportsRecyclerview.adapter = adapter
            sportsRecyclerview.itemAnimator = null
        }
    }

    private fun navigateToNews(news: TopNews) {
        val direction =
            HomeViewPageFragmentDirections.homeViewPageFragmentToNewsDetailFragment(news.urlToArticle)
        findNavController().navigate(direction)
    }

    private fun initSwipeRefreshLayout(binding: FragmentSportsBinding) {
        with(binding.swipeToRefreshSports) {
            setColorSchemeResources(R.color.colorPrimary)
            setOnRefreshListener {
                sportsViewModel.refreshNews()
            }
        }
    }

    private fun initListData() {
        sportsViewModel.sportsNews.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })
    }

}