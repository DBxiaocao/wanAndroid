package com.xiocao.wanandroid.ui.project

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import android.view.View
import com.xiocao.wanandroid.R
import com.xiocao.wanandroid.base.ArchBaseFragment
import com.xiocao.wanandroid.base.FragmentPagerAdapter
import com.xiocao.wanandroid.retrofit.ErrorStatus
import com.xiocao.wanandroid.utils.ToastUtils
import kotlinx.android.synthetic.main.fragment_project.*
import java.util.*

/**
 * User : lijun
 * Date : 2018/7/4  15:01
 * Content : This is
 */
class ProjectFragment : ArchBaseFragment<ProjectViewModel>() {

    companion object {
        fun newInstance(): ProjectListFragment {
            return ProjectListFragment()
        }
    }

    override fun initViewModel() {
        mViewModel = ViewModelProviders.of(this).get(ProjectViewModel::class.java)
        mViewModel.initRepository()
    }

    override fun getResLayout(): Int {
        return R.layout.fragment_project
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val titles = LinkedList<String>()
        val fragments = LinkedList<androidx.fragment.app.Fragment>()
        mViewModel.getError().observe(this, Observer<ErrorStatus> { error -> ToastUtils.showShortToast(mActivity, error?.message.toString()) })
        mViewModel.getTitle().observe(this, Observer<List<ProjectTitle>> { title ->
            title?.forEach { bean ->
                titles.add(bean.name.toString())
                fragments.add(ProjectListFragment.newInstance(bean.id))
            }
            val mFragmentPagerAdapter = FragmentPagerAdapter(childFragmentManager)
            mFragmentPagerAdapter.run {
                setFragments(fragments)
                setTabTitleList(titles)
            }
            tabViewPager.run {
                adapter = mFragmentPagerAdapter
            }
            tabLayout.run {
                setupWithViewPager(tabViewPager)
            }
        })
    }

}