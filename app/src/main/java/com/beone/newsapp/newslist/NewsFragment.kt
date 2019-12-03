package com.beone.newsapp.newslist


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

import com.beone.newsapp.databinding.FragmentNewsBinding

/**
 * A simple [Fragment] subclass.
 */
class NewsFragment : Fragment() {

    private val newsViewModel: NewsViewModel by lazy {
        ViewModelProviders.of(this).get(NewsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentNewsBinding.inflate(inflater)
        val adapter = NewsGridAdapter()
        binding.apply {
            lifecycleOwner = this@NewsFragment
            photosGrid.adapter = adapter
        }

        binding.viewModel = newsViewModel
        newsViewModel.topHeadlines.observe(viewLifecycleOwner, Observer{
            it?.let {
                adapter.submitList(it)
            }
        })
        setHasOptionsMenu(true)
        return binding.root
    }
}
