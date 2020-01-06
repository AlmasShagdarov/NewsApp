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
import com.beone.newsapp.databinding.FragmentScienceBinding
import com.beone.newsapp.domain.TopNews
import com.beone.newsapp.extensions.isNetworkAvailable
import com.beone.newsapp.network.ApiStatus
import com.beone.newsapp.viewmodel.ScienceViewModel
import com.beone.newsapp.viewmodel.ScienceViewModelFactory

class ScienceFragment : Fragment() {

    private val scienceViewModel: ScienceViewModel by lazy {
        val activity = requireNotNull(value = this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        ViewModelProviders.of(this, ScienceViewModelFactory(activity.application))
            .get(ScienceViewModel::class.java)
    }

    private lateinit var adapter: TopNewsListAdapter
    private lateinit var binding: FragmentScienceBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentScienceBinding.inflate(inflater)
        initBinding(binding)
        initSwipeRefreshLayout(binding)
        initListData()
        observeStatus()
        return binding.root
    }

    private fun observeStatus() {
        scienceViewModel.status.observe(viewLifecycleOwner, Observer { status ->
            @Suppress("WHEN_ENUM_CAN_BE_NULL_IN_JAVA")
            when (status) {
                ApiStatus.DONE -> {
                    binding.swipeToRefreshScience.isRefreshing = false
                    binding.txtConnectionScience.visibility = View.GONE
                }
                ApiStatus.ERROR -> {
                    when (requireNotNull(value = this.activity).isNetworkAvailable()) {
                        true -> binding.txtConnectionScience.text = "Server not responding"
                        false -> binding.txtConnectionScience.text = "No internet connection"
                    }
                    binding.swipeToRefreshScience.isRefreshing = false
                    binding.txtConnectionScience.visibility = View.VISIBLE
                    binding.txtConnectionScience.postDelayed({
                        binding.txtConnectionScience.visibility = View.GONE
                    }, 3000)
                }
            }
        })
    }

    private fun initBinding(binding: FragmentScienceBinding) {
        adapter = TopNewsListAdapter(NewsListener { navigateToNews(it) })
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            scienceRecyclerview.adapter = adapter
            scienceRecyclerview.itemAnimator = null
        }
    }

    private fun navigateToNews(news: TopNews) {
        val direction =
            HomeViewPageFragmentDirections.homeViewPageFragmentToNewsDetailFragment(news.urlToArticle)
        findNavController().navigate(direction)
    }

    private fun initSwipeRefreshLayout(binding: FragmentScienceBinding) {
        with(binding.swipeToRefreshScience) {
            setColorSchemeResources(R.color.colorPrimary)
            setOnRefreshListener {
                scienceViewModel.refreshNews()
            }
        }
    }

    private fun initListData() {
        scienceViewModel.scienceNews.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })
    }

}