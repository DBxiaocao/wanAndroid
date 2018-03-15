package com.xiocao.wanandroid.ui.home

import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import com.orhanobut.logger.Logger
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import com.xiocao.wanandroid.R
import com.xiocao.wanandroid.app.App
import com.xiocao.wanandroid.base.BaseActivity
import com.xiocao.wanandroid.base.SimpleRecyclerAdapter
import com.xiocao.wanandroid.retrofit.rx.RxCallback
import com.xiocao.wanandroid.ui.web.WebActivity
import com.xiocao.wanandroid.utils.ToastUtils
import com.xiocao.wanandroid.view.DiverItemDecoration
import kotlinx.android.synthetic.main.include_recyclerview.*

class TypeActivity : BaseActivity(), OnRefreshListener, OnLoadmoreListener {
    lateinit var listAdapter: SimpleRecyclerAdapter<TypeList.DatasBean>
    override fun onRefresh(refreshlayout: RefreshLayout?) {
        page = 0
        queryData()
    }

    override fun onLoadmore(refreshlayout: RefreshLayout?) {
        queryData()
    }

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
        refreshLayout.setOnRefreshListener(this)
        refreshLayout.setOnLoadmoreListener(this)
        refreshLayout.autoRefresh()
        listAdapter = SimpleRecyclerAdapter<TypeList.DatasBean>(R.layout.item_home_list_data)
                .setOnBindViewListener { _, bean, view ->
                    var viewHolder = ViewHolder(view)
                    viewHolder.setModel(bean)
                    viewHolder.setOnItemClick(View.OnClickListener {
                        var bundle = Bundle()
                        bundle.putString(WebActivity.KEY_WEB_URL, bean.link)
                        startActivity(Intent(mActivity, WebActivity::class.java).putExtras(bundle))
                    })
                }
        mRecyclerView.run {
            layoutManager = object : LinearLayoutManager(mActivity) {}
            addItemDecoration(DiverItemDecoration(
                    ContextCompat.getColor(mActivity, R.color.colorBg), 15))
            adapter = listAdapter
        }
    }

    private fun queryData() {
        doNetRequest(App.getApiService().getTypeList(page, intent.getIntExtra(KEY_CID, 0)), object : RxCallback<TypeList> {
            override fun onSuccess(model: TypeList) {
                Logger.e("--------------------------" + model.over)
                if (model.over)
                    refreshLayout.finishLoadmoreWithNoMoreData()
                else
                    refreshLayout.resetNoMoreData()
                refreshLayout.finishLoadmore()
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
            }

            override fun onFailure(code: Int, msg: String) {
                ToastUtils.showShortToast(mActivity, msg)
            }
        })
    }

    internal class ViewHolder(view: View) {
        private var tvTitle: TextView = view.findViewById(R.id.tvDataTitle)
        private var tvAuthor: TextView = view.findViewById(R.id.tvAuthor)
        private var tvSuperChapterName: TextView = view.findViewById(R.id.tvSuperChapterName)
        private var tvNiceDate: TextView = view.findViewById(R.id.tvNiceDate)
        private var rlHomeItem: RelativeLayout = view.findViewById(R.id.rlHomeItem)
        fun setModel(bean: TypeList.DatasBean) {
            tvTitle.text = bean.title
            tvAuthor.text = bean.author
            tvSuperChapterName.visibility = View.GONE
            tvNiceDate.text = bean.niceDate
        }

        fun setOnItemClick(onClickListener: View.OnClickListener) {
            rlHomeItem.setOnClickListener(onClickListener)
        }
    }
}
