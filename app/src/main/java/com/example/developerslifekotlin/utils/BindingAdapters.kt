package com.example.developerslifekotlin.utils

import android.view.View
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.developerslifekotlin.R
import com.example.developerslifekotlin.presentation.gifview.DevelopersLifeApiStatus

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
        imgUrl?.let {
            val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
            Glide.with(imgView.context)
                .load(imgUri)
                .apply(
                    RequestOptions()
                        .placeholder(R.drawable.loading_animation)
                        .error(R.drawable.ic_broken_image)
                )
                //.centerCrop()
                .into(imgView)
        }
}

@BindingAdapter("imageStatus")
fun bindStatus(statusImageView: ImageView, status: DevelopersLifeApiStatus?) {
    when (status) {
        DevelopersLifeApiStatus.LOADING -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.loading_animation)
        }
        DevelopersLifeApiStatus.DONE -> {
            statusImageView.visibility = View.GONE
        }
        else -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_connection_error)
        }
    }
}

@BindingAdapter("imageGifStatus")
fun bindGifStatus(statusImageView: ImageView, status: DevelopersLifeApiStatus?) {
    when (status) {
        DevelopersLifeApiStatus.LOADING -> {
            statusImageView.visibility = View.GONE
        }
        DevelopersLifeApiStatus.DONE -> {
            statusImageView.visibility = View.VISIBLE
        }
        else -> {
            statusImageView.visibility = View.GONE
        }
    }
}
