package com.xiocao.wanandroid.ui.home

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.View
import android.widget.ImageView
import com.xiocao.wanandroid.R
import com.xiocao.wanandroid.base.SimpleRecyclerAdapter
import com.xiocao.wanandroid.helper.BannerPageAdapterHelper
import com.xiocao.wanandroid.utils.ImageUtils
import com.xiocao.wanandroid.utils.ToastUtils
import kotlinx.android.synthetic.main.fragment_home.*
import com.xiocao.wanandroid.view.DiverItemDecoration
import com.xiocao.wanandroid.base.ArchBaseFragment
import com.xiocao.wanandroid.retrofit.ErrorStatus
import com.xiocao.wanandroid.ui.home.viewmodel.HomeViewModel
import com.xiocao.wanandroid.ui.web.WebActivity
import kotlinx.android.synthetic.main.item_home_list_data.view.*
import kotlin.math.log


/**
 * User : lijun
 * Date : 2018/3/13  11:15
 * Content : This is
 */
class HomeFragment : ArchBaseFragment<HomeViewModel>() {
    override fun getResLayout(): Int {
        return R.layout.fragment_home
    }

    override fun initViewModel() {
        mViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        mViewModel.initRepository()
    }

    private var page: Int = 0
    private var listAdapter = SimpleRecyclerAdapter<HomeList.DatasBean>(R.layout.item_home_list_data)

    companion object {
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mActivity.setCenterTitle("首页")
        listAdapter.setOnBindViewListener { _, bean, view ->
            view.tvDataTitle.text = bean.title
            view.tvAuthor.text = bean.author
            view.tvSuperChapterName.run {
                text = bean.superChapterName
                setOnClickListener {
                    val bundle = Bundle()
                    bundle.putInt(TypeActivity.KEY_CID, bean.chapterId)
                    bundle.putString(TypeActivity.KEY_CID_NAME, bean.superChapterName)
                    startActivity(Intent(mActivity, TypeActivity::class.java).putExtras(bundle))
                }
            }
            view.tvNiceDate.text = bean.niceDate
            view.setOnClickListener {
                val bundle = Bundle()
                bundle.putString(WebActivity.KEY_WEB_URL, bean.link)
                bundle.putInt(WebActivity.KEY_WEB_ID, bean.id)
                bundle.putBoolean(WebActivity.KEY_WEB_COLLECT, bean.isCollect)
                startActivity(Intent(mActivity, WebActivity::class.java).putExtras(bundle))
            }
        }
        mRecyclerView.run {
            layoutManager = object : androidx.recyclerview.widget.LinearLayoutManager(mActivity) {}
            addItemDecoration(DiverItemDecoration(
                    ContextCompat.getColor(mActivity, R.color.colorBg), 15))
            adapter = listAdapter
            isNestedScrollingEnabled = false
        }
        refreshLayout.run {
            setOnRefreshListener {
                page = 0
                getBanner()
                getListData()
            }
            setOnLoadMoreListener {
                getListData()
            }
            autoRefresh()
        }
        mViewModel.getError().observe(this, Observer<ErrorStatus> { error ->
            refreshLayout.run {
                finishLoadMore()
                finishRefresh()
            }
            if (error != null) {
                ToastUtils.showShortToast(mActivity, error.message.toString())
            }
        })

    }

    private fun getBanner() {
        mViewModel.getBanner().observe(this, Observer<List<HomeBanner>> { data ->
            if (data != null) {
                setModelBanner(data)
            }
        })
    }

    private fun getListData() {
        mViewModel.getList(page).observe(this, Observer<HomeList> { data ->
            refreshLayout.finishRefresh()
            refreshLayout.finishRefresh()
            if (data != null) {
                if (page == 0)
                    listAdapter.setDataList(data.datas)
                else
                    listAdapter.addDataList(data.datas)
                page = data.curPage
                listAdapter.notifyDataSetChanged()
            }
        })
    }

    private fun setModelBanner(bannerList: List<HomeBanner>) {
        val bannerAdapter = BannerPageAdapterHelper(bannerList)
        bannerAdapter.run {
            setOnItemClickListener(object : BannerPageAdapterHelper.OnItemClickListener {
                override fun onClick(view: View, position: Int) {
                    val bean = bannerList[mViewPager.currentItem]
                    val bundle = Bundle()
                    bundle.putString(WebActivity.KEY_WEB_URL, bean.url)
                    bundle.putInt(WebActivity.KEY_WEB_ID, bean.id)
                    bundle.putBoolean(WebActivity.KEY_WEB_COLLECT, false)
                    startActivity(Intent(mActivity, WebActivity::class.java).putExtras(bundle))
                }
            })
            setOnInstantiateItemListener(object : BannerPageAdapterHelper.OnInstantiateItemListener<HomeBanner> {
                override fun instantiateItem(imageView: ImageView, imageUrl: HomeBanner) {
                    ImageUtils.displayDefault(mActivity, imageUrl.imagePath.toString(), imageView)
                }
            })
        }
        mViewPager.adapter = bannerAdapter
        mPageIndicator.setViewPager(mViewPager)
    }

}
