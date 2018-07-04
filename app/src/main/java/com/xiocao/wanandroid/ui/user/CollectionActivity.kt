package com.xiocao.wanandroid.ui.user

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import com.xiocao.wanandroid.R
import com.xiocao.wanandroid.base.ArchBaseActivity
import com.xiocao.wanandroid.base.SimpleRecyclerAdapter
import com.xiocao.wanandroid.retrofit.ErrorStatus
import com.xiocao.wanandroid.ui.home.TypeList
import com.xiocao.wanandroid.ui.user.viewmodel.CollectionViewModel
import com.xiocao.wanandroid.ui.web.WebActivity
import com.xiocao.wanandroid.utils.ToastUtils
import com.xiocao.wanandroid.view.DiverItemDecoration
import kotlinx.android.synthetic.main.include_recyclerview.*
import kotlinx.android.synthetic.main.item_home_list_data.view.*

class CollectionActivity : ArchBaseActivity<CollectionViewModel>(), OnRefreshListener, OnLoadmoreListener {
    override fun initViewModel() {
        mViewModel = ViewModelProviders.of(mActivity).get(CollectionViewModel::class.java)
        mViewModel.initRepository()
    }

    lateinit var listAdapter: SimpleRecyclerAdapter<TypeList.DatasBean>
    override fun onRefresh(refreshlayout: RefreshLayout?) {
        page = 0
        queryData()
    }

    override fun onLoadmore(refreshlayout: RefreshLayout?) {
        queryData()
    }

    var page: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collection)
        setEnabledNavigation(true)
        setCenterTitle("我的收藏")
        refreshLayout.setOnRefreshListener(this)
        refreshLayout.setOnLoadmoreListener(this)
        refreshLayout.autoRefresh()
        listAdapter = SimpleRecyclerAdapter<TypeList.DatasBean>(R.layout.item_home_list_data)
                .setOnBindViewListener { _, bean, view ->
                    view.tvDataTitle.text = bean.title
                    view.tvAuthor.text = bean.author
                    view.tvSuperChapterName.visibility = View.GONE
                    view.tvNiceDate.text = bean.niceDate
                    view.run {
                        setOnClickListener {
                            val bundle = Bundle()
                            bundle.putString(WebActivity.KEY_WEB_URL, bean.link)
                            bundle.putInt(WebActivity.KEY_WEB_ID, bean.id)
                            bundle.putBoolean(WebActivity.KEY_WEB_COLLECT, true)
                            startActivity(Intent(mActivity, WebActivity::class.java).putExtras(bundle))
                        }
                    }
                }
        mRecyclerView.run {
            layoutManager = object : LinearLayoutManager(mActivity) {}
            addItemDecoration(DiverItemDecoration(
                    ContextCompat.getColor(mActivity, R.color.colorBg), 15))
            adapter=listAdapter
        }
        mViewModel.getError().observe(this, Observer<ErrorStatus> { error -> ToastUtils.showShortToast(mActivity, error?.message.toString()) })
    }

    private fun queryData() {
        mViewModel.getCollection(page).observe(this, Observer<TypeList> { model ->
            if (model != null) {
                if (model.over)
                    refreshLayout.finishLoadmoreWithNoMoreData()
                else
                    refreshLayout.resetNoMoreData()
                refreshLayout.finishRefresh()
                refreshLayout.finishLoadmore()
                if (model.datas.isNotEmpty()) {
                    if (page == 0) {
                        listAdapter.setDataList(model.datas)
                    } else {
                        listAdapter.addDataList(model.datas)
                    }
                    page = model.curPage
                    listAdapter.notifyDataSetChanged()
                }
            }
        })
    }
}
