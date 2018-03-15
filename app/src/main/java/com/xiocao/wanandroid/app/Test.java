package com.xiocao.wanandroid.app;

import android.app.Application;

import com.xiocao.wanandroid.ui.category.Cate;

import java.util.ArrayList;
import java.util.List;

/**
 * User : lijun
 * Date : 2018/3/13  17:27
 * Content : This is
 */
public class Test extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        List<Cate.ChildrenBean> beans=new ArrayList<>();
        for (Cate.ChildrenBean bean : beans) {
        }
    }
}
