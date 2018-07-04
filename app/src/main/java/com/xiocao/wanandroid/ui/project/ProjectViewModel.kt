package com.xiocao.wanandroid.ui.project

import android.arch.lifecycle.LiveData
import com.xiocao.wanandroid.base.BaseViewModel

/**
 * User : lijun
 * Date : 2018/7/4  15:13
 * Content : This is
 */
class ProjectViewModel : BaseViewModel<ProjectRepository>() {
    override fun initRepository() {
        repository = ProjectRepository()
    }

    fun getTitle(): LiveData<List<ProjectTitle>> {
        return repository.getProjectTitle()
    }

    fun getList(page: Int, cid: Int): LiveData<Projects> {
        return repository.getProjects(page, cid)
    }
}
