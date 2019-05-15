package com.xiocao.wanandroid.helper

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

class FragmentHelper(private val mContext: Context, private val mFragmentManager: androidx.fragment.app.FragmentManager, private val mFrameRes: Int) {
    private val mFragmentItems = mutableMapOf<String, FragmentInfo>()
    private var mShowingInfo: FragmentInfo? = null

    class FragmentInfo {
        var tag: String? = null
            private set
        var fragment: androidx.fragment.app.Fragment? = null
            private set
        internal var isAdd: Boolean = false

        constructor(fragment: androidx.fragment.app.Fragment) {
            tag = fragment.javaClass.simpleName
            this.fragment = fragment
        }

        constructor(tag: String, fragment: androidx.fragment.app.Fragment) {
            this.tag = tag
            this.fragment = fragment
        }
    }

    init {
        mFragmentItems.clear()
    }

    /**
     * Add fragment info for map.
     *
     * @param info FragmentInfo
     */
    fun addFragmentItem(info: FragmentInfo) {
        mFragmentItems.put(info.tag!!, info)
    }

    /**
     * Show fragment by tag.
     *
     * @param tag String
     */
    fun show(tag: String) {
        show(mFragmentItems[tag]!!)
    }


    /**
     * Judge the tag's fragment is showing.
     *
     * @param tag String
     * @return String
     */
    fun isShowing(tag: String): Boolean {
        return mShowingInfo!!.tag == tag
    }

    /**
     * Show fragment by info.
     *
     * @param info FragmentInfo
     */
    fun show(info: FragmentInfo) {

        val trans = mFragmentManager.beginTransaction()
                .disallowAddToBackStack()

        if (mShowingInfo === info) {
            // Fragment is showing, don't try
            // again
        } else {
            if (mShowingInfo != null) {
                // Detach showing fragment
                if (mShowingInfo!!.fragment != null) {
                    trans.hide(mShowingInfo!!.fragment!!)
                }
            }
            mShowingInfo = info
            if (mShowingInfo != null) {
                // Attach init fragment
                if (!mShowingInfo!!.isAdd) {
                    mShowingInfo!!.isAdd = true
                    trans.add(mFrameRes, mShowingInfo!!.fragment!!, mShowingInfo!!.tag)
                } else {
                    trans.show(mShowingInfo!!.fragment!!)
                }
            }
        }

        if (!trans.isEmpty) {
            //            trans.commit();
            trans.commitAllowingStateLoss()
        }
    }
}
