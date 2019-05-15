package com.xiocao.wanandroid.ui.category

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.xiocao.wanandroid.R
import com.xiocao.wanandroid.app.App
import com.xiocao.wanandroid.base.BaseFragment
import com.xiocao.wanandroid.base.SimpleRecyclerAdapter
import com.xiocao.wanandroid.retrofit.rx.RxCallback
import com.xiocao.wanandroid.ui.home.TypeActivity
import com.xiocao.wanandroid.utils.ToastUtils
import com.xiocao.wanandroid.view.DiverItemDecoration
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.item_cate_list.view.*

/**
 * User : lijun
 * Date : 2018/3/13  11:15
 * Content : This is
 */
class CateFragment : BaseFragment() {

    lateinit var listAdapter: SimpleRecyclerAdapter<Cate>

    companion object {
        fun newInstance(): CateFragment {
            return CateFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_cate, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listAdapter = SimpleRecyclerAdapter<Cate>(R.layout.item_cate_list)
                .setOnBindViewListener { _, bean, view ->
                    view.tvCateGroupName.text = bean.name
                    val childAdapter=SimpleRecyclerAdapter<Cate.ChildrenBean>(R.layout.item_cate_child_tag).setDataList(bean.children)
                            .setOnBindViewListener { _, t, view ->
                                val tvCateChildName: TextView = view as TextView
                                tvCateChildName.run {
                                    text=t.name
                                    setOnClickListener {
                                        val bundle = Bundle()
                                        bundle.putInt(TypeActivity.KEY_CID, t.id)
                                        bundle.putString(TypeActivity.KEY_CID_NAME, t.name)
                                        startActivity(Intent(mActivity, TypeActivity::class.java).putExtras(bundle))
                                    }
                                }
                            }
                    view.mRvChild.run {
                        val manager = FlexboxLayoutManager(mActivity)
                        manager.flexWrap = FlexWrap.WRAP
                        layoutManager=manager
                        adapter=childAdapter
                    }
                }
        mRecyclerView.run {
            layoutManager=object : androidx.recyclerview.widget.LinearLayoutManager(mActivity) {}
            addItemDecoration(DiverItemDecoration(
                    ContextCompat.getColor(mActivity, R.color.colorBg), 15))
            adapter = listAdapter
        }
        doNetRequest(App.getApiService().getCateInfo(), object : RxCallback<List<Cate>> {
            override fun onSuccess(model: List<Cate>) {
                listAdapter.dataList = model
                listAdapter.notifyDataSetChanged()
            }

            override fun onFailure(code: Int, msg: String) {
                ToastUtils.showShortToast(mActivity, msg)
            }
        })
    }
}

