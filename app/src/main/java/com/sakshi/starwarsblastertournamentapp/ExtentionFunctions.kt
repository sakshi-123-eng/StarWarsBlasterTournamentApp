package com.sakshi.starwarsblastertournamentapp

import android.graphics.drawable.Drawable
import android.util.Log
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

inline fun ImageView.loadImage(
    imageUrl: String?,
    crossinline onSuccess: () -> Unit = {},
    crossinline onFailed: () -> Unit = {}
) {
    imageUrl?.let {
        Glide.with(this)
            .load(it)
            .diskCacheStrategy(DiskCacheStrategy.DATA)
            .addListener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    onFailed.invoke()
                    Log.d("jgvhbmn", e?.message.toString())
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    onSuccess.invoke()
                    Log.d("jgvhbmn", "onResourceReady Called")
                    return false
                }

            })
            .into(this)
    }
}