package com.beone.newsapp.ui

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer

import com.beone.newsapp.R
import com.beone.newsapp.adapter.BusinessNewsListAdapter
import com.beone.newsapp.adapter.TopNewsListAdapter
import com.beone.newsapp.databinding.FragmentBusinessNewsBinding
import com.beone.newsapp.databinding.FragmentNewsBinding
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

    private lateinit var adapter: BusinessNewsListAdapter
    private lateinit var binding: FragmentBusinessNewsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBusinessNewsBinding.inflate(inflater)
        initBinding(binding)
        initSwipeRefreshLayout(binding)
        initListData()
        setHasOptionsMenu(true)
        return binding.root
    }

    private fun initBinding(binding: FragmentBusinessNewsBinding) {
        adapter = BusinessNewsListAdapter()
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            businessRecyclerview.adapter = adapter
        }
    }

    private fun initSwipeRefreshLayout(binding: FragmentBusinessNewsBinding) {
        val swipeToRefreshBusiness = binding.swipeToRefreshBusiness
        with(swipeToRefreshBusiness) {
            setColorSchemeResources(R.color.colorPrimary)
            setOnRefreshListener {
                businessViewModel.refreshNews()
                swipeToRefreshBusiness.isRefreshing = false
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
