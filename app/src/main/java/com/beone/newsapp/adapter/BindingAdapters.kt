package com.beone.newsapp.adapter

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.beone.newsapp.R
import com.beone.newsapp.util.eraseCharNumber
import com.beone.newsapp.util.format
import com.beone.newsapp.util.humanizeDiff
import com.beone.newsapp.util.toDate
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        Glide.with(imgView.context)
            .load(imgUrl)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.loading_animation)
                    .error(R.drawable.broken_image)
            )
            .into(imgView)
    }
}


@BindingAdapter("date")
fun setDate(txtView: TextView, txt: String?) {
    txtView.text = txt?.toDate()?.humanizeDiff()
}

@BindingAdapter("dateFull")
fun setFullDate(txtView: TextView, txt: String?) {
    txtView.text = txt?.toDate()?.format("EEE, d MMM yyyy HH:mm")
}


@BindingAdapter("author")
fun setLiveContent(txtView: TextView, txt: String?) {
    val modifiedStr = "by $txt"
    txtView.text = modifiedStr
}

@BindingAdapter("content")
fun setContent(txtView: TextView, txt: String?) {
    txt?.let {
        txtView.text = it.eraseCharNumber()
    }
}

