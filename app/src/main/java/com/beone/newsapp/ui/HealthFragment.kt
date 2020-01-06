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
import com.beone.newsapp.databinding.FragmentHealthBinding
import com.beone.newsapp.domain.TopNews
import com.beone.newsapp.extensions.isNetworkAvailable
import com.beone.newsapp.network.ApiStatus
import com.beone.newsapp.viewmodel.HealthViewModel
import com.beone.newsapp.viewmodel.HealthViewModelFactory

class HealthFragment : Fragment() {

    private val healthViewModel: HealthViewModel by lazy {
        val activity = requireNotNull(value = this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        ViewModelProviders.of(this, HealthViewModelFactory(activity.application))
            .get(HealthViewModel::class.java)
    }

    private lateinit var adapter: TopNewsListAdapter
    private lateinit var binding: FragmentHealthBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHealthBinding.inflate(inflater)
        initBinding(binding)
        initSwipeRefreshLayout(binding)
        initListData()
        observeStatus()
        return binding.root
    }

    private fun observeStatus() {
        healthViewModel.status.observe(viewLifecycleOwner, Observer { status ->
            @Suppress("WHEN_ENUM_CAN_BE_NULL_IN_JAVA")
            when (status) {
                ApiStatus.DONE -> {
                    binding.swipeToRefreshHealth.isRefreshing = false
                    binding.txtConnectionHealth.visibility = View.GONE
                }
                ApiStatus.ERROR -> {
                    when (requireNotNull(value = this.activity).isNetworkAvailable()) {
                        true -> binding.txtConnectionHealth.text = "Server not responding"
                        false -> binding.txtConnectionHealth.text = "No internet connection"
                    }
                    binding.swipeToRefreshHealth.isRefreshing = false
                    binding.txtConnectionHealth.visibility = View.VISIBLE
                    binding.txtConnectionHealth.postDelayed({
                        binding.txtConnectionHealth.visibility = View.GONE
                    }, 3000)
                }
            }
        })
    }

    private fun initBinding(binding: FragmentHealthBinding) {
        adapter = TopNewsListAdapter(NewsListener { navigateToNews(it) })
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            healthRecyclerview.adapter = adapter
            healthRecyclerview.itemAnimator = null
        }
    }

    private fun navigateToNews(news: TopNews) {
        val direction =
            HomeViewPageFragmentDirections.homeViewPageFragmentToNewsDetailFragment(news.urlToArticle)
        findNavController().navigate(direction)
    }

    private fun initSwipeRefreshLayout(binding: FragmentHealthBinding) {
        with(binding.swipeToRefreshHealth) {
            setColorSchemeResources(R.color.colorPrimary)
            setOnRefreshListener {
                healthViewModel.refreshNews()
            }
        }
    }

    private fun initListData() {
        healthViewModel.healthNews.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })
    }

}