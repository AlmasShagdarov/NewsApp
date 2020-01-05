package com.beone.newsapp.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ShareCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.beone.newsapp.R
import com.beone.newsapp.databinding.FragmentNewsDetailBinding
import com.beone.newsapp.util.eraseCharNumber
import com.beone.newsapp.viewmodel.NewsDetailViewModel
import com.beone.newsapp.viewmodel.NewsDetailViewModelFactory
import com.google.android.material.snackbar.Snackbar


class NewsDetailFragment : Fragment() {

    private val args: NewsDetailFragmentArgs by navArgs()

    private val newsDetailViewModel: NewsDetailViewModel by lazy {
        val activity = requireNotNull(value = this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        ViewModelProviders.of(this, NewsDetailViewModelFactory(activity.application, args.newsId))
            .get(NewsDetailViewModel::class.java)
    }

    lateinit var binding: FragmentNewsDetailBinding
    var isFavorite: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewsDetailBinding.inflate(inflater)
        initBinding()
        initTextDeepLink(binding)
        observeFavorites()
        setupToolbar()
        return binding.root

    }

    private fun setupToolbar() {
        val newsDetailToolbar = binding.newsDetailToolbar
        with(newsDetailToolbar) {
            setNavigationOnClickListener {
                findNavController().navigateUp()
            }
            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.action_share -> {
                        createShareIntent()
                        true
                    }
                    R.id.action_add_note -> {

                        when (isFavorite) {
                            true -> newsDetailViewModel.removeFromFavorites()
                            false -> {
                                newsDetailViewModel.addToFavorites()
                                val mySnackbar = Snackbar.make(
                                    binding.coordinatorLayout,
                                    "Added to Favorites", Snackbar.LENGTH_SHORT
                                )
                                mySnackbar.show()

                            }
                        }
                        true
                    }
                    else -> false
                }
            }
        }
    }

    private fun observeFavorites() {
        newsDetailViewModel.isFavorite.observe(viewLifecycleOwner, Observer {
            isFavorite = it
            val item = binding.newsDetailToolbar.menu.findItem(R.id.action_add_note)
            when (it) {
                true -> item.setIcon(R.drawable.ic_add_favorites_active)
                false -> item.setIcon(R.drawable.ic_add_favorites_inactive)
            }
        })
    }

    private fun initBinding() {
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = newsDetailViewModel
            newsDetailToolbar.elevation = 8f
        }
    }

    private fun initTextDeepLink(binding: FragmentNewsDetailBinding) {
        newsDetailViewModel.topNewsById.observe(viewLifecycleOwner, Observer {
            with(binding.txtContent) {
                text = formatContent(it.content, it.urlToArticle)
                movementMethod = LinkMovementMethod.getInstance()
            }

        })
    }

    private fun formatContent(content: String?, url: String): SpannableStringBuilder {
        val builder = SpannableStringBuilder()
        val spannableStr = SpannableString("more")
        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                val browserIntent =
                    Intent(Intent.ACTION_VIEW, Uri.parse(url))
                requireNotNull(this@NewsDetailFragment.activity).startActivity(browserIntent)
            }
        }
        spannableStr.setSpan(
            clickableSpan,
            0,
            spannableStr.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        builder.append(content!!.eraseCharNumber())
        builder.append(spannableStr)
        return builder
    }

    private fun createShareIntent() {
        val shareText = newsDetailViewModel.topNewsById.value!!.urlToArticle
        val shareIntent = ShareCompat.IntentBuilder.from(activity)
            .setText(shareText)
            .setType("text/plain")
            .createChooserIntent()
        startActivity(shareIntent)
    }

}