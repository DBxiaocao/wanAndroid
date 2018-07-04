package com.xiocao.wanandroid.ui.search

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.Gravity
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.RelativeLayout
import android.widget.TextView
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import com.xiocao.wanandroid.R
import com.xiocao.wanandroid.app.App
import com.xiocao.wanandroid.base.ArchBaseActivity
import com.xiocao.wanandroid.base.BaseActivity
import com.xiocao.wanandroid.base.SimpleRecyclerAdapter
import com.xiocao.wanandroid.retrofit.ErrorStatus
import com.xiocao.wanandroid.retrofit.rx.RxCallback
import com.xiocao.wanandroid.ui.home.TypeActivity
import com.xiocao.wanandroid.ui.home.TypeList
import com.xiocao.wanandroid.ui.web.WebActivity
import com.xiocao.wanandroid.utils.ToastUtils
import com.xiocao.wanandroid.view.DiverItemDecoration
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.include_recyclerview.*
import kotlinx.android.synthetic.main.item_home_list_data.view.*
import kotlinx.android.synthetic.main.toolbar_search_layout.*

class SearchActivity : ArchBaseActivity<SearchViewModel>(), OnRefreshListener, OnLoadmoreListener {
    override fun initViewModel() {
        mViewModel = ViewModelProviders.of(mActivity).get(SearchViewModel::class.java)
        mViewModel.initRepository()
    }

    private var page: Int = 0
    lateinit var listAdapter: SimpleRecyclerAdapter<TypeList.DatasBean>
    lateinit var hotKeyAdapter: SimpleRecyclerAdapter<HotSearch>
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
                    view.tvDataTitle.text = bean.title
                    view.tvAuthor.text = bean.author
                    view.tvSuperChapterName.run {
                        text = bean.superChapterName
                        visibility = if (TextUtils.isEmpty(bean.superChapterName))
                            View.GONE
                        else
                            View.VISIBLE
                        setOnClickListener {
                            val bundle = Bundle()
                            bundle.putInt(TypeActivity.KEY_CID, bean.chapterId)
                            bundle.putString(TypeActivity.KEY_CID_NAME, bean.chapterName)
                            startActivity(Intent(mActivity, TypeActivity::class.java).putExtras(bundle))
                        }
                    }
                    view.tvNiceDate.text = bean.niceDate
                    view.run {
                        setOnClickListener {
                            val bundle = Bundle()
                            bundle.putString(WebActivity.KEY_WEB_URL, bean.link)
                            bundle.putInt(WebActivity.KEY_WEB_ID, bean.id)
                            bundle.putBoolean(WebActivity.KEY_WEB_COLLECT, bean.isCollect)
                            startActivity(Intent(mActivity, WebActivity::class.java).putExtras(bundle))
                        }
                    }
                }
        mRecyclerView.run {
            layoutManager = object : LinearLayoutManager(mActivity) {}
            addItemDecoration(DiverItemDecoration(
                    ContextCompat.getColor(mActivity, R.color.colorBg), 15))
            adapter = listAdapter
        }

        hotKeyAdapter = SimpleRecyclerAdapter<HotSearch>(R.layout.item_cate_child_tag)
                .setOnBindViewListener { _, t, view ->
                    val tvCateChildName: TextView = view as TextView
                    tvCateChildName.run {
                        text = t.name
                        setOnClickListener {
                            mEditSearch.setText(text.toString())
                            mEditSearch.setSelection(text.toString().length)
                            page = 0
                            queryKey()
                        }
                    }
                }

        hotRvKey.run {
            val manager = FlexboxLayoutManager(mActivity)
            manager.flexWrap = FlexWrap.WRAP
            layoutManager = manager
            adapter = hotKeyAdapter
        }
        mViewModel.getError().observe(this, Observer<ErrorStatus> { error -> ToastUtils.showShortToast(mActivity, error?.message.toString()) })
        mViewModel.getHotKey().observe(this, Observer<List<HotSearch>> { data -> hotKeyAdapter.setDataList(data)})
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
            setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    page = 0
                    queryKey()
                    true
                } else
                    false
            }
            addTextChangedListener(object :TextWatcher{
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                }

                override fun afterTextChanged(s: Editable) {
                    if (s.isEmpty()){
                        hotRvKey.visibility=View.VISIBLE
                        mRecyclerView.visibility=View.GONE
                    }
                }
            })
        }
    }

    private fun queryKey() {
        mViewModel.getSearchList(page, mEditSearch.text.toString()).observe(this, Observer<TypeList> { model ->
            hotRvKey.visibility=View.GONE
            mRecyclerView.visibility=View.VISIBLE
            refreshLayout.finishRefresh()
            refreshLayout.finishLoadmore()
            if (page == 0)
                listAdapter.setDataList(model?.datas)
            else
                listAdapter.addDataList(model?.datas)
            page = model!!.curPage
            listAdapter.notifyDataSetChanged()
        })
    }
}
