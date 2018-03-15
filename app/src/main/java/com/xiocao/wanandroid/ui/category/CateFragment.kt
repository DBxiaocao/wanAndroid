package com.xiocao.wanandroid.ui.category

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.xiocao.wanandroid.R
import com.xiocao.wanandroid.app.App
import com.xiocao.wanandroid.base.BaseFragment
import com.xiocao.wanandroid.base.SimpleRecyclerAdapter
import com.xiocao.wanandroid.retrofit.rx.RxCallback
import com.xiocao.wanandroid.ui.home.TypeActivity
import com.xiocao.wanandroid.utils.ToastUtils
import com.xiocao.wanandroid.view.DiverItemDecoration
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * User : lijun
 * Date : 2018/3/13  11:15
 * Content : This is
 */
class CateFragment : BaseFragment() {

    lateinit var listAdapter: SimpleRecyclerAdapter<Cate>

    companion object {
        fun newInstance(): CateFragment {
            val args = Bundle()
            val fragment = CateFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_cate, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listAdapter = SimpleRecyclerAdapter<Cate>(R.layout.item_cate_list)
                .setOnBindViewListener { _, bean, view ->
                    var viewHolder = ViewHolder(view)
                    viewHolder.setModel(bean)
                    viewHolder.onItemClick(View.OnClickListener {
//                        startActivity(Intent(activity, CateInfoActivity::class.java))
                    })
                }
        mRecyclerView.run {
            layoutManager=object : LinearLayoutManager(mActivity) {}
            addItemDecoration(DiverItemDecoration(
                    ContextCompat.getColor(mActivity, R.color.colorBg), 15))
            adapter = listAdapter
        }
        doNetRequest(App.getApiService().getCateInfo(), object : RxCallback<List<Cate>> {
            override fun onSuccess(model: List<Cate>) {
                listAdapter.dataList = model
                listAdapter.notifyDataSetChanged()
            }

            override fun onFailure(code: Int, msg: String) {
                ToastUtils.showShortToast(mActivity, msg)
            }
        })
    }

    internal class ViewHolder(view: View) {
        private var tvGroupName: TextView = view.findViewById(R.id.tvCateGroupName)
        var mRvChild: RecyclerView = view.findViewById(R.id.mRvChild)
        var rlCateItem: RelativeLayout = view.findViewById(R.id.rlCateItem)
        fun setModel(bean: Cate) {
            tvGroupName.text = bean.name
            val layoutManager = FlexboxLayoutManager(mRvChild.context)
            layoutManager.flexWrap = FlexWrap.WRAP
//            layoutManager.alignItems = AlignItems.STRETCH
            mRvChild.layoutManager = layoutManager
            var childAdapter=SimpleRecyclerAdapter<Cate.ChildrenBean>(R.layout.item_cate_child_tag).setDataList(bean.children)
                    .setOnBindViewListener { _, t, view ->
                        var tvCateChildName: TextView = view as TextView
                        tvCateChildName.setOnClickListener {
                            var bundle = Bundle()
                            bundle.putInt(TypeActivity.KEY_CID, t.id)
                            bundle.putString(TypeActivity.KEY_CID_NAME, t.name)
                            mRvChild.context.startActivity(Intent(mRvChild.context, TypeActivity::class.java).putExtras(bundle))
                        }
                        if (t != null) {
                            tvCateChildName.text=t.name
                        }
                    }
            mRvChild.adapter=childAdapter
        }

        fun onItemClick(onClickListener: View.OnClickListener) {
            rlCateItem.setOnClickListener(onClickListener)
        }

    }

}

