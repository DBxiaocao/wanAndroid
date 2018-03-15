package com.xiocao.wanandroid.ui.category

import android.os.Parcelable

/**
 * User : lijun
 * Date : 2018/3/13  17:08
 * Content : This is
 */
class Cate {

    /**
     * children : [{"children":[],"courseId":13,"id":60,"name":"Android Studio相关","order":1000,"parentChapterId":150,"visible":1},{"children":[],"courseId":13,"id":169,"name":"gradle","order":1001,"parentChapterId":150,"visible":1},{"children":[],"courseId":13,"id":269,"name":"官方发布","order":1002,"parentChapterId":150,"visible":1}]
     * courseId : 13
     * id : 150
     * name : 开发环境
     * order : 1
     * parentChapterId : 0
     * visible : 1
     */

    var courseId: Int = 0
    var id: Int = 0
    var name: String? = null
    var order: Int = 0
    var parentChapterId: Int = 0
    var visible: Int = 0
    var children = mutableListOf<ChildrenBean>()


    class ChildrenBean {
        /**
         * children : []
         * courseId : 13
         * id : 60
         * name : Android Studio相关
         * order : 1000
         * parentChapterId : 150
         * visible : 1
         */

        var courseId: Int = 0
        var id: Int = 0
        lateinit var name: String
        var order: Int = 0
        var parentChapterId: Int = 0
        var visible: Int = 0
        var children: List<*>? = null
    }
}
