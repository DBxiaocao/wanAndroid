package com.xiocao.wanandroid.utils

import android.annotation.SuppressLint
import android.content.Context
import android.widget.ImageView

import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

/**
 * className: GlideUtils
 * author: lijun
 * date: 17/7/5 17:23
 */

object ImageUtils {
//    private val mOptions = RequestOptions()
//            .placeholder(android.R.color.white)
//            .error(android.R.color.white)

    fun displayDefault(context: Context, url: String, imageView: ImageView) {
        Glide.with(context)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(imageView)
    }

}
