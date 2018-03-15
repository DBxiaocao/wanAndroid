package com.xiocao.wanandroid.ui.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.xiocao.wanandroid.R
import com.xiocao.wanandroid.base.BaseFragment

/**
 * User : lijun
 * Date : 2018/3/13  18:29
 * Content : This is
 */
class CateListFragment : BaseFragment() {
    companion object {
        private val KEY_CATE_ID: String = "key.cate.id"
        fun newInstance(cateId: Int): CateListFragment {
            val args = Bundle()
            val fragment = CateListFragment()
            args.putInt(KEY_CATE_ID,cateId)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_user, container, false)
    }
}
