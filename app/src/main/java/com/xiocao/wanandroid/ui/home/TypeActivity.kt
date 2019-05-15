package com.xiocao.wanandroid.ui.home

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import com.xiocao.wanandroid.R
import com.xiocao.wanandroid.base.ArchBaseActivity
import com.xiocao.wanandroid.base.SimpleRecyclerAdapter
import com.xiocao.wanandroid.retrofit.ErrorStatus
import com.xiocao.wanandroid.ui.home.viewmodel.TypeViewModel
import com.xiocao.wanandroid.ui.web.WebActivity
import com.xiocao.wanandroid.utils.ToastUtils
import com.xiocao.wanandroid.view.DiverItemDecoration
import kotlinx.android.synthetic.main.include_recyclerview.*
import kotlinx.android.synthetic.main.item_home_list_data.view.*

class TypeActivity : ArchBaseActivity<TypeViewModel>(){
    override fun initViewModel() {
        mViewModel = ViewModelProviders.of(mActivity).get(TypeViewModel::class.java)
        mViewModel.initRepository()
    }

    private val listAdapter= SimpleRecyclerAdapter<TypeList.DatasBean>(R.layout.item_home_list_data)

    var page: Int = 0

    companion object {
        val KEY_CID: String = "key.cid"
        val KEY_CID_NAME: String = "key.cid.name"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_type)
        setEnabledNavigation(true)
        setCenterTitle(intent.getStringExtra(KEY_CID_NAME))

        refreshLayout.run {
            setOnRefreshListener {
                page = 0
                queryData()
            }
            setOnLoadMoreListener {
                queryData()
            }
            autoRefresh()
        }

        listAdapter.setOnBindViewListener { _, bean, view ->
                    view.tvDataTitle.text = bean.title
                    view.tvAuthor.text = bean.author
                    view.tvSuperChapterName.text = bean.superChapterName
                    view.tvNiceDate.text = bean.niceDate
                    view.setOnClickListener {
                        val bundle = Bundle()
                        bundle.putString(WebActivity.KEY_WEB_URL, bean.link)
                        startActivity(Intent(mActivity, WebActivity::class.java).putExtras(bundle))
                    }
                }
        mRecyclerView.run {
            layoutManager = object : androidx.recyclerview.widget.LinearLayoutManager(mActivity) {}
            addItemDecoration(DiverItemDecoration(
                    ContextCompat.getColor(mActivity, R.color.colorBg), 15))
            adapter = listAdapter
        }
        mViewModel.getError().observe(this, Observer<ErrorStatus> { err ->
            if (err != null) {
                ToastUtils.showShortToast(mActivity, err.message.orEmpty())
            }
        })
    }

    private fun queryData() {
        mViewModel.getTypes(page, intent.getIntExtra(KEY_CID, 0)).observe(this, Observer<TypeList> { model ->
            if (model!!.over)
                refreshLayout.finishLoadMoreWithNoMoreData()
            else
                refreshLayout.resetNoMoreData()
            refreshLayout.finishLoadMore()
            if (model.datas.isNotEmpty()) {
                if (page == 0) {
                    refreshLayout.finishRefresh()
                    listAdapter.setDataList(model.datas)
                } else {
                    listAdapter.addDataList(model.datas)
                }
                page = model.curPage
                listAdapter.notifyDataSetChanged()
            }
        })
    }

}
