package com.xiocao.wanandroid.ui.project

import android.arch.lifecycle.MutableLiveData
import com.xiocao.wanandroid.app.App
import com.xiocao.wanandroid.base.BaseRepository
import com.xiocao.wanandroid.retrofit.ErrorStatus
import com.xiocao.wanandroid.retrofit.HttpUtil
import com.xiocao.wanandroid.retrofit.rx.RxCallback

/**
 * User : lijun
 * Date : 2018/7/4  15:02
 * Content : This is
 */
class ProjectRepository : BaseRepository() {
    fun getProjectTitle(): MutableLiveData<List<ProjectTitle>> {
        val title = MutableLiveData<List<ProjectTitle>>()
        HttpUtil.startRetrofitCall(App.getApiService().getProjectTitle(), object : RxCallback<List<ProjectTitle>> {
            override fun onFailure(code: Int, msg: String) {
                error.postValue(ErrorStatus.error(msg, code))
            }

            override fun onSuccess(model: List<ProjectTitle>) {
                title.postValue(model)
            }
        })
        return title
    }

    fun getProjects(page: Int, cid: Int): MutableLiveData<Projects> {
        val title = MutableLiveData<Projects>()
        HttpUtil.startRetrofitCall(App.getApiService().getProjectList(page,cid), object : RxCallback<Projects> {
            override fun onFailure(code: Int, msg: String) {
                error.postValue(ErrorStatus.error(msg, code))
            }

            override fun onSuccess(model: Projects) {
                title.postValue(model)
            }
        })
        return title
    }


}