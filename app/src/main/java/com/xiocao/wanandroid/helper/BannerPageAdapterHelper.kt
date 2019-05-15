package com.xiocao.wanandroid.helper

import androidx.viewpager.widget.PagerAdapter
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView

/**
 * 广告图片适配器
 */
class BannerPageAdapterHelper<T>(private val mPagerImageList: List<T>) : androidx.viewpager.widget.PagerAdapter() {
    private val mSize: Int = mPagerImageList.size

    private var mOnItemClickListener: OnItemClickListener? = null
    private var mOnInstantiateItemListener: OnInstantiateItemListener<T>? = null

     fun getItem(position: Int): T {
        return mPagerImageList[position]
    }

    override fun getCount(): Int {
        return mSize
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun destroyItem(view: ViewGroup, position: Int, `object`: Any) {
        view.removeView(`object` as View)
    }

    override fun instantiateItem(view: ViewGroup, position: Int): Any {

        val imageView = ImageView(view.context)
        imageView.setOnClickListener(OnViewClickListener(position))
        imageView.scaleType = ImageView.ScaleType.FIT_XY
        view.addView(imageView, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT)


        val imageUrl = getItem(position)
        if (mOnInstantiateItemListener != null)
            mOnInstantiateItemListener!!.instantiateItem(imageView, imageUrl)
        return imageView
    }

    private inner class OnViewClickListener internal constructor(private val mPosition: Int) : View.OnClickListener {

        /**
         * Called when a view has been clicked.
         *
         * @param v The view that was clicked.
         */
        override fun onClick(v: View) {
            if (mOnItemClickListener != null)
                mOnItemClickListener!!.onClick(v, mPosition)
        }
    }


    fun setOnItemClickListener(l: OnItemClickListener?) {
        mOnItemClickListener = l
    }

    interface OnItemClickListener {
        fun onClick(view: View, position: Int)
    }

    fun setOnInstantiateItemListener(l: OnInstantiateItemListener<T>): BannerPageAdapterHelper<*> {
        mOnInstantiateItemListener = l
        return this
    }

    interface OnInstantiateItemListener<T> {
        fun instantiateItem(imageView: ImageView, imageUrl: T)
    }

}