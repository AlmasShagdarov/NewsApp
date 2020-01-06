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
import com.beone.newsapp.databinding.FragmentTechnologyBinding
import com.beone.newsapp.domain.TopNews
import com.beone.newsapp.extensions.isNetworkAvailable
import com.beone.newsapp.network.ApiStatus
import com.beone.newsapp.viewmodel.*

class TechnologyFragment : Fragment() {

    private val techViewModel: TechnologyViewModel by lazy {
        val activity = requireNotNull(value = this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        ViewModelProviders.of(this, TechnologyViewModelFactory(activity.application))
            .get(TechnologyViewModel::class.java)
    }
    private lateinit var adapter: TopNewsListAdapter
    private lateinit var binding: FragmentTechnologyBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTechnologyBinding.inflate(inflater)
        initBinding(binding)
        initSwipeRefreshLayout(binding)
        initListData()
        observeStatus()
        return binding.root
    }

    private fun observeStatus() {
        techViewModel.status.observe(viewLifecycleOwner, Observer { status ->
            @Suppress("WHEN_ENUM_CAN_BE_NULL_IN_JAVA")
            when (status) {
                ApiStatus.DONE -> {
                    binding.swipeToRefreshTechnology.isRefreshing = false
                    binding.txtConnectionTechnology.visibility = View.GONE
                }
                ApiStatus.ERROR -> {
                    when (requireNotNull(value = this.activity).isNetworkAvailable()) {
                        true -> binding.txtConnectionTechnology.text = "Server not responding"
                        false -> binding.txtConnectionTechnology.text = "No internet connection"
                    }
                    binding.swipeToRefreshTechnology.isRefreshing = false
                    binding.txtConnectionTechnology.visibility = View.VISIBLE
                    binding.txtConnectionTechnology.postDelayed({
                        binding.txtConnectionTechnology.visibility = View.GONE
                    }, 3000)
                }
            }
        })
    }

    private fun initBinding(binding: FragmentTechnologyBinding) {
        adapter = TopNewsListAdapter(NewsListener { navigateToNews(it) })
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            technologyRecyclerview.adapter = adapter
            technologyRecyclerview.itemAnimator = null
        }
    }

    private fun navigateToNews(news: TopNews) {
        val direction =
            HomeViewPageFragmentDirections.homeViewPageFragmentToNewsDetailFragment(news.urlToArticle)
        findNavController().navigate(direction)
    }

    private fun initSwipeRefreshLayout(binding: FragmentTechnologyBinding) {
        with(binding.swipeToRefreshTechnology) {
            setColorSchemeResources(R.color.colorPrimary)
            setOnRefreshListener {
                techViewModel.refreshNews()
            }
        }
    }

    private fun initListData() {
        techViewModel.techNews.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })
    }

}