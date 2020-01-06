package com.beone.newsapp.ui

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.beone.newsapp.R
import com.beone.newsapp.adapter.NewsListener
import com.beone.newsapp.adapter.TopNewsListAdapter
import com.beone.newsapp.databinding.FragmentBusinessNewsBinding
import com.beone.newsapp.domain.TopNews
import com.beone.newsapp.extensions.isNetworkAvailable
import com.beone.newsapp.network.ApiStatus
import com.beone.newsapp.viewmodel.BusinessViewModel
import com.beone.newsapp.viewmodel.BusinessViewModelFactory

class BusinessFragment : Fragment() {

    private val businessViewModel: BusinessViewModel by lazy {
        val activity = requireNotNull(value = this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        ViewModelProviders.of(this, BusinessViewModelFactory(activity.application))
            .get(BusinessViewModel::class.java)
    }

    private lateinit var adapter: TopNewsListAdapter
    private lateinit var binding: FragmentBusinessNewsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBusinessNewsBinding.inflate(inflater)
        initBinding(binding)
        initSwipeRefreshLayout(binding)
        initListData()
        observeStatus()
        return binding.root
    }

    private fun observeStatus() {
        businessViewModel.status.observe(viewLifecycleOwner, Observer { status ->
            @Suppress("WHEN_ENUM_CAN_BE_NULL_IN_JAVA")
            when (status) {
                ApiStatus.DONE -> {
                    binding.swipeToRefreshBusiness.isRefreshing = false
                    binding.txtConnectionBusiness.visibility = View.GONE
                }
                ApiStatus.ERROR -> {
                    when (requireNotNull(value = this.activity).isNetworkAvailable()) {
                        true -> binding.txtConnectionBusiness.text = "Server not responding"
                        false -> binding.txtConnectionBusiness.text = "No internet connection"
                    }
                    binding.swipeToRefreshBusiness.isRefreshing = false
                    binding.txtConnectionBusiness.visibility = View.VISIBLE
                    binding.txtConnectionBusiness.postDelayed({
                        binding.txtConnectionBusiness.visibility = View.GONE
                    }, 3000)
                }
            }
        })
    }

    private fun initBinding(binding: FragmentBusinessNewsBinding) {
        adapter = TopNewsListAdapter(NewsListener { navigateToNews(it) })
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            businessRecyclerview.adapter = adapter
            businessRecyclerview.itemAnimator = null
        }
    }

    private fun navigateToNews(news: TopNews) {
        val direction =
            HomeViewPageFragmentDirections.homeViewPageFragmentToNewsDetailFragment(news.urlToArticle)
        findNavController().navigate(direction)
    }
    private fun initSwipeRefreshLayout(binding: FragmentBusinessNewsBinding) {
        with(binding.swipeToRefreshBusiness) {
            setColorSchemeResources(R.color.colorPrimary)
            setOnRefreshListener {
                businessViewModel.refreshNews()
            }
        }
    }

    private fun initListData() {
        businessViewModel.businessNews.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })
    }

}
