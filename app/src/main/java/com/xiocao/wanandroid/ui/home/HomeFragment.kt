package com.xiocao.wanandroid.ui.home

import android.content.Intent
import android.os.Binder
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import com.xiocao.wanandroid.R
import com.xiocao.wanandroid.app.App
import com.xiocao.wanandroid.base.BaseFragment
import com.xiocao.wanandroid.base.SimpleRecyclerAdapter
import com.xiocao.wanandroid.helper.BannerPageAdapterHelper
import com.xiocao.wanandroid.retrofit.rx.RxCallback
import com.xiocao.wanandroid.utils.ImageUtils
import com.xiocao.wanandroid.utils.ToastUtils
import kotlinx.android.synthetic.main.fragment_home.*
import com.xiocao.wanandroid.view.DiverItemDecoration
import android.widget.TextView
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import com.xiocao.wanandroid.ui.web.WebActivity


/**
 * User : lijun
 * Date : 2018/3/13  11:15
 * Content : This is
 */
class HomeFragment : BaseFragment(), OnRefreshListener, OnLoadmoreListener {
    override fun onLoadmore(refreshlayout: RefreshLayout) {
        page = 1
        getBanner()
        getListData()
    }

    override fun onRefresh(refreshlayout: RefreshLayout) {
        getBanner()
        getListData()
    }

    var page: Int = 0
    lateinit var listAdapter: SimpleRecyclerAdapter<HomeList.DatasBean>

    companion object {
        fun newInstance(): HomeFragment {
            val args = Bundle()
            val fragment = HomeFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mActivity.setCenterTitle("首页")
        listAdapter = SimpleRecyclerAdapter<HomeList.DatasBean>(R.layout.item_home_list_data)
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
            isNestedScrollingEnabled = false
        }
        refreshLayout.setOnRefreshListener(this)
        refreshLayout.setOnLoadmoreListener(this)
        refreshLayout.autoRefresh()
//        getBanner()
//        getListData()
    }

    private fun getBanner() {
        doNetRequest(App.getApiService().getHomeBanner(), object : RxCallback<List<HomeBanner>> {
            override fun onSuccess(model: List<HomeBanner>) {
                if (model.isNotEmpty())
                    setModelBanner(model)
            }

            override fun onFailure(code: Int, msg: String) {
                ToastUtils.showShortToast(mActivity, msg)
            }
        })
    }

    private fun getListData() {
        doNetRequest(App.getApiService().getHomeList(page), object : RxCallback<HomeList> {
            override fun onSuccess(model: HomeList) {
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

    private fun setModelBanner(bannerList: List<HomeBanner>) {
        mViewPager.adapter = BannerPageAdapterHelper(bannerList)
                .setOnInstantiateItemListener(object : BannerPageAdapterHelper.OnInstantiateItemListener<HomeBanner> {
                    override fun instantiateItem(imageView: ImageView, imageUrl: HomeBanner) {
                        ImageUtils.displayDefault(mActivity, imageUrl.imagePath.toString(), imageView)
                    }
                })
        mPageIndicator.setViewPager(mViewPager)
    }

    internal class ViewHolder(view: View) {
        private var tvTitle: TextView = view.findViewById(R.id.tvDataTitle)
        private var tvAuthor: TextView = view.findViewById(R.id.tvAuthor)
        private var tvSuperChapterName: TextView = view.findViewById(R.id.tvSuperChapterName)
        private var tvNiceDate: TextView = view.findViewById(R.id.tvNiceDate)
        private var rlHomeItem: RelativeLayout = view.findViewById(R.id.rlHomeItem)
        fun setModel(bean: HomeList.DatasBean) {
            tvTitle.text = bean.title
            tvAuthor.text = bean.author
            tvSuperChapterName.text = bean.superChapterName
            tvNiceDate.text = bean.niceDate
        }

        fun setOnItemClick(onClickListener: View.OnClickListener) {
            rlHomeItem.setOnClickListener(onClickListener)
            tvSuperChapterName.setOnClickListener(onClickListener)
        }
    }

}
