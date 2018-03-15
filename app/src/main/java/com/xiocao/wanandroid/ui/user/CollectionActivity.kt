package com.xiocao.wanandroid.ui.user

import android.content.Intent
import android.support.v7.app.AppCompatActivity
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
import com.xiocao.wanandroid.ui.home.TypeList
import com.xiocao.wanandroid.ui.web.WebActivity
import com.xiocao.wanandroid.utils.ToastUtils
import com.xiocao.wanandroid.view.DiverItemDecoration
import kotlinx.android.synthetic.main.include_recyclerview.*

class CollectionActivity : BaseActivity() ,OnRefreshListener,OnLoadmoreListener{

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
        mRecyclerView.layoutManager = object : LinearLayoutManager(mActivity){}
        mRecyclerView.addItemDecoration(DiverItemDecoration(
                ContextCompat.getColor(mActivity, R.color.colorBg), 15))
        listAdapter = SimpleRecyclerAdapter<TypeList.DatasBean>(R.layout.item_home_list_data)
                .setOnBindViewListener { _, bean, view ->
                    var viewHolder = ViewHolder(view)
                    viewHolder.setModel(bean)
                    viewHolder.setOnItemClick(View.OnClickListener {
                        var bundle = Bundle()
                        bundle.putString(WebActivity.KEY_WEB_URL, bean.link)
                        bundle.putInt(WebActivity.KEY_WEB_ID,bean.id)
                        bundle.putBoolean(WebActivity.KEY_WEB_COLLECT,true)
                        startActivity(Intent(mActivity, WebActivity::class.java).putExtras(bundle))
                    })
                }
        mRecyclerView.adapter = listAdapter
    }

    private fun queryData() {
        doNetRequest(App.getApiService().getCollection(page), object : RxCallback<TypeList> {
            override fun onSuccess(model: TypeList) {
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
                    page=model.curPage
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
            tvSuperChapterName.visibility= View.GONE
            tvNiceDate.text = bean.niceDate
        }

        fun setOnItemClick(onClickListener: View.OnClickListener) {
            rlHomeItem.setOnClickListener(onClickListener)
        }
    }
}
