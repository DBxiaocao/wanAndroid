package com.xiocao.wanandroid.ui.project

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.View
import com.xiocao.wanandroid.R
import com.xiocao.wanandroid.base.ArchBaseFragment
import com.xiocao.wanandroid.base.SimpleRecyclerAdapter
import com.xiocao.wanandroid.ui.web.WebActivity
import com.xiocao.wanandroid.utils.ImageUtils
import com.xiocao.wanandroid.view.DiverItemDecoration
import kotlinx.android.synthetic.main.include_recyclerview.*
import kotlinx.android.synthetic.main.item_project_list_data.view.*

/**
 * User : lijun
 * Date : 2018/7/4  15:29
 * Content : This is
 */
class ProjectListFragment : ArchBaseFragment<ProjectViewModel>() {
    var page: Int = 1
    val listAdapter = SimpleRecyclerAdapter<Projects.DatasBean>(R.layout.item_project_list_data)

    companion object {
        private val KEY_CATE_ID: String = "key.project.id"
        fun newInstance(cid: Int): ProjectListFragment {
            val args = Bundle()
            args.putInt(KEY_CATE_ID, cid)
            val fragment = ProjectListFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun initViewModel() {
        mViewModel = ViewModelProviders.of(mActivity).get(ProjectViewModel::class.java)
        mViewModel.initRepository()
    }

    override fun getResLayout(): Int {
        return R.layout.include_recyclerview
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        refreshLayout.run {
            setOnRefreshListener {
                page = 1
                queryProject()
            }
            setOnLoadMoreListener {
                queryProject()
            }
            refreshLayout.autoRefresh()
        }
        mRecyclerView.run {
            layoutManager = androidx.recyclerview.widget.LinearLayoutManager(mActivity)
            addItemDecoration(DiverItemDecoration(
                    ContextCompat.getColor(mActivity, R.color.colorBg), 15))
            adapter = listAdapter
            isNestedScrollingEnabled = false
        }
        listAdapter.run {
            setOnBindViewListener { _, bean, view ->
                val img = view.ivProjectPic
                ImageUtils.displayDefault(mActivity, bean.envelopePic.toString(), img)
                view.tvProjectDese.text = bean.desc
                view.tvProjectTitle.text = bean.title
                view.tvProjectNiceDate.text = bean.niceDate
                view.tvProjectAuthor.text = bean.author
                view.setOnClickListener {
                    val bundle = Bundle()
                    bundle.putString(WebActivity.KEY_WEB_URL, bean.link)
                    bundle.putInt(WebActivity.KEY_WEB_ID, bean.id)
                    bundle.putBoolean(WebActivity.KEY_WEB_COLLECT, bean.collect)
                    startActivity(Intent(mActivity, WebActivity::class.java).putExtras(bundle))
                }
            }
        }
    }

    fun queryProject() {
        mViewModel.getList(page, arguments!!.getInt(KEY_CATE_ID, 0)).observe(this, Observer { data ->
            refreshLayout.run {
                finishRefresh()
                finishLoadMore()
            }
            if (data != null) {
                if (page == 1)
                    listAdapter.setDataList(data.datas)
                else
                    listAdapter.addDataList(data.datas)
                if (page < data.pageCount) {
                    page += 1
                    refreshLayout.resetNoMoreData()
                } else {
                    page = data.pageCount
                    refreshLayout.finishLoadMoreWithNoMoreData()
                }
                listAdapter.notifyDataSetChanged()
            }
        })
    }

}