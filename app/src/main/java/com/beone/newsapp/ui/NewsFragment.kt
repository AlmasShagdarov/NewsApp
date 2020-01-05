package com.beone.newsapp.ui


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.beone.newsapp.R
import com.beone.newsapp.adapter.TopNewsListAdapter
import com.beone.newsapp.databinding.FragmentNewsBinding
import com.beone.newsapp.util.isNetworkAvailable
import com.beone.newsapp.viewmodel.ApiStatus
import com.beone.newsapp.viewmodel.NewsViewModel
import com.beone.newsapp.viewmodel.NewsViewModelFactory
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*


/**
 * A simple [Fragment] subclass.
 */
class NewsFragment : Fragment() {

    private val newsViewModel: NewsViewModel by lazy {
        val activity = requireNotNull(value = this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        ViewModelProviders.of(this, NewsViewModelFactory(activity.application))
            .get(NewsViewModel::class.java)
    }

    private lateinit var adapter: TopNewsListAdapter
    private lateinit var binding: FragmentNewsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewsBinding.inflate(inflater)
        initBinding()
        initSwipeRefreshLayout(binding)
        initListData()
        newsViewModel.status.observe(viewLifecycleOwner, Observer { status ->
            @Suppress("WHEN_ENUM_CAN_BE_NULL_IN_JAVA")
            when (status) {
                ApiStatus.DONE -> {
                    binding.swipeToRefresh.isRefreshing = false
                    binding.txtConnection.visibility = View.GONE
                }
                ApiStatus.ERROR -> {
                    when (requireNotNull(value = this.activity).isNetworkAvailable()) {
                        true -> binding.txtConnection.text = "Server not responding"
                        false -> binding.txtConnection.text = "No internet connection"
                    }
                    binding.swipeToRefresh.isRefreshing = false
                    binding.txtConnection.visibility = View.VISIBLE
                    binding.txtConnection.postDelayed({
                        binding.txtConnection.visibility = View.GONE
                    }, 3000)
                }
            }
        })

        return binding.root
    }


    private fun initBinding() {
        adapter = TopNewsListAdapter()
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            newsRecyclerview.adapter = adapter
        }
    }

    private fun initListData() {
        newsViewModel.networkTopHeadlines.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })
    }

    private fun initSwipeRefreshLayout(binding: FragmentNewsBinding) {
        val swipeRefreshLayout = binding.swipeToRefresh
        with(swipeRefreshLayout) {
            setColorSchemeResources(R.color.colorPrimary)
            setOnRefreshListener {
                newsViewModel.refreshNews()
            }
        }
    }

}
