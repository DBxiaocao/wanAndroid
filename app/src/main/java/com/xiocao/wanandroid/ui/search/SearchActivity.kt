package com.xiocao.wanandroid.ui.search

import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.text.TextUtils
import android.view.Gravity
import android.view.KeyEvent
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import com.xiocao.wanandroid.R
import com.xiocao.wanandroid.app.App
import com.xiocao.wanandroid.base.BaseActivity
import com.xiocao.wanandroid.base.SimpleRecyclerAdapter
import com.xiocao.wanandroid.retrofit.rx.RxCallback
import com.xiocao.wanandroid.ui.home.TypeActivity
import com.xiocao.wanandroid.ui.home.TypeList
import com.xiocao.wanandroid.ui.web.WebActivity
import com.xiocao.wanandroid.utils.ToastUtils
import com.xiocao.wanandroid.view.DiverItemDecoration
import kotlinx.android.synthetic.main.include_recyclerview.*
import kotlinx.android.synthetic.main.toolbar_search_layout.*

class SearchActivity : BaseActivity(), OnRefreshListener, OnLoadmoreListener {

    private var page: Int = 0
    private var key: String = ""
    lateinit var listAdapter: SimpleRecyclerAdapter<TypeList.DatasBean>
    override fun onRefresh(refreshlayout: RefreshLayout?) {
        page = 0
        queryKey()
    }

    override fun onLoadmore(refreshlayout: RefreshLayout?) {
        queryKey()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        initMenu()
        initView()
    }

    private fun initView() {
        refreshLayout.setOnRefreshListener(this)
        refreshLayout.setOnLoadmoreListener(this)
        listAdapter = SimpleRecyclerAdapter<TypeList.DatasBean>(R.layout.item_home_list_data)
                .setOnBindViewListener { _, bean, view ->
                    var viewHolder = ViewHolder(view)
                    viewHolder.setModel(bean)
                    viewHolder.setOnItemClick(View.OnClickListener { v ->
                        when (v?.id) {
                            R.id.rlHomeItem -> {
                                var bundle = Bundle()
                                bundle.putString(WebActivity.KEY_WEB_URL, bean.link)
                                bundle.putInt(WebActivity.KEY_WEB_ID, bean.id)
                                bundle.putBoolean(WebActivity.KEY_WEB_COLLECT, bean.isCollect)
                                startActivity(Intent(mActivity, WebActivity::class.java).putExtras(bundle))
                            }
                            R.id.tvSuperChapterName -> {
                                var bundle = Bundle()
                                bundle.putInt(TypeActivity.KEY_CID, bean.chapterId)
                                bundle.putString(TypeActivity.KEY_CID_NAME, bean.chapterName)
                                startActivity(Intent(mActivity, TypeActivity::class.java).putExtras(bundle))
                            }
                        }
                    })
                }
        mRecyclerView.run {
            layoutManager = object : LinearLayoutManager(mActivity) {}
            addItemDecoration(DiverItemDecoration(
                    ContextCompat.getColor(mActivity, R.color.colorBg), 15))
            adapter = listAdapter
        }
    }

    private fun initMenu() {
        val params = Toolbar.LayoutParams(
                Toolbar.LayoutParams.MATCH_PARENT, Toolbar.LayoutParams.MATCH_PARENT
        )
        params.gravity = Gravity.LEFT
        val view = View.inflate(mActivity, R.layout.toolbar_search_layout, null)
        getToolBar().addView(view, params)
        mIvBack.setOnClickListener {
            onBackPressed()
        }
        mEditSearch.run {
            setOnEditorActionListener { _, _, event ->
                if (event.keyCode == KeyEvent.KEYCODE_ENTER || event.keyCode == KeyEvent.KEYCODE_SEARCH) {
                    key = mEditSearch.text.toString()
                    queryKey()
                    true
                } else
                    false
            }
        }
    }

    private fun queryKey() {
        if (TextUtils.isEmpty(key)) {
            ToastUtils.showShortToast(mActivity, "关键字不能为空")
            return
        }
        doNetRequest(App.getApiService().getSearch(page, key), object : RxCallback<TypeList> {
            override fun onSuccess(model: TypeList) {
                refreshLayout.finishRefresh()
                refreshLayout.finishLoadmore()
                if (page == 0)
                    listAdapter.setDataList(model.datas)
                else
                    listAdapter.addDataList(model.datas)
                page = model.curPage
                listAdapter.notifyDataSetChanged()
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
            tvSuperChapterName.run {
                text = bean.superChapterName
                visibility = if (TextUtils.isEmpty(bean.superChapterName))
                    View.GONE
                else
                    View.VISIBLE
            }
            tvNiceDate.text = bean.niceDate
        }

        fun setOnItemClick(onClickListener: View.OnClickListener) {
            rlHomeItem.setOnClickListener(onClickListener)
            tvSuperChapterName.setOnClickListener(onClickListener)
        }
    }
}
