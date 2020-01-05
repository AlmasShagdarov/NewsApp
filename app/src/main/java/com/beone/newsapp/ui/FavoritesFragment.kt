package com.beone.newsapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.beone.newsapp.R
import com.beone.newsapp.adapter.FavoritesListAdapter
import com.beone.newsapp.databinding.FragmentFavoritesBinding
import com.beone.newsapp.viewmodel.FavoritesViewModel
import com.beone.newsapp.viewmodel.FavoritesViewModelFactory

class FavoritesFragment : Fragment() {

    private val favoritesViewModel: FavoritesViewModel by lazy {
        val activity = requireNotNull(value = this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        ViewModelProviders.of(this, FavoritesViewModelFactory(activity.application))
            .get(FavoritesViewModel::class.java)
    }

    private lateinit var adapter: FavoritesListAdapter
    private lateinit var binding: FragmentFavoritesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoritesBinding.inflate(inflater)
        val searchItem = binding.favoritesToolbar.menu.findItem(R.id.action_search)
        val search = searchItem.actionView as SearchView
        search.queryHint = "Search"
        search.imeOptions = EditorInfo.IME_ACTION_DONE
        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                favoritesViewModel.filterList(newText).observe(viewLifecycleOwner, Observer {
                    it?.let {
                        adapter.submitList(it)
                    }
                })
                return true
            }

        })


        initBinding()
        initListData()
        return binding.root
    }

    private fun initBinding() {
        adapter = FavoritesListAdapter()
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            favoritesRecyclerview.adapter = adapter
        }
        binding.favoritesToolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun initListData() {
        favoritesViewModel.filterList().observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })
    }
}