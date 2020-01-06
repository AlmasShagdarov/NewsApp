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
import com.beone.newsapp.databinding.FragmentEntertainmentBinding
import com.beone.newsapp.domain.TopNews
import com.beone.newsapp.extensions.isNetworkAvailable
import com.beone.newsapp.network.ApiStatus
import com.beone.newsapp.viewmodel.EntertainmentViewModel
import com.beone.newsapp.viewmodel.EntertainmentViewModelFactory

class EntertainmentFragment : Fragment() {

    private val entertainmentViewModel: EntertainmentViewModel by lazy {
        val activity = requireNotNull(value = this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        ViewModelProviders.of(this, EntertainmentViewModelFactory(activity.application))
            .get(EntertainmentViewModel::class.java)
    }

    private lateinit var adapter: TopNewsListAdapter
    private lateinit var binding: FragmentEntertainmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEntertainmentBinding.inflate(inflater)
        initBinding(binding)
        initSwipeRefreshLayout(binding)
        initListData()
        observeStatus()
        return binding.root
    }

    private fun observeStatus() {
        entertainmentViewModel.status.observe(viewLifecycleOwner, Observer { status ->
            @Suppress("WHEN_ENUM_CAN_BE_NULL_IN_JAVA")
            when (status) {
                ApiStatus.DONE -> {
                    binding.swipeToRefreshEntertainment.isRefreshing = false
                    binding.txtConnectionEntertainment.visibility = View.GONE
                }
                ApiStatus.ERROR -> {
                    when (requireNotNull(value = this.activity).isNetworkAvailable()) {
                        true -> binding.txtConnectionEntertainment.text = "Server not responding"
                        false -> binding.txtConnectionEntertainment.text = "No internet connection"
                    }
                    binding.swipeToRefreshEntertainment.isRefreshing = false
                    binding.txtConnectionEntertainment.visibility = View.VISIBLE
                    binding.txtConnectionEntertainment.postDelayed({
                        binding.txtConnectionEntertainment.visibility = View.GONE
                    }, 3000)
                }
            }
        })
    }

    private fun initBinding(binding: FragmentEntertainmentBinding) {
        adapter = TopNewsListAdapter(NewsListener { navigateToNews(it) })
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            entertainmentRecyclerview.adapter = adapter
            entertainmentRecyclerview.itemAnimator = null
        }
    }

    private fun navigateToNews(news: TopNews) {
        val direction =
            HomeViewPageFragmentDirections.homeViewPageFragmentToNewsDetailFragment(news.urlToArticle)
        findNavController().navigate(direction)
    }

    private fun initSwipeRefreshLayout(binding: FragmentEntertainmentBinding) {
        with(binding.swipeToRefreshEntertainment) {
            setColorSchemeResources(R.color.colorPrimary)
            setOnRefreshListener {
                entertainmentViewModel.refreshNews()
            }
        }
    }

    private fun initListData() {
        entertainmentViewModel.entertainmentNews.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })
    }

}