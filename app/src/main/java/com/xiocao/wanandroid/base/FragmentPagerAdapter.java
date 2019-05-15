package com.xiocao.wanandroid.base;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.List;



public class FragmentPagerAdapter extends FragmentStatePagerAdapter {

    private int mTabCount;
    private List<String> mTabTitleList;
    private List<Fragment> mFragments;

    public FragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public FragmentPagerAdapter(FragmentManager fm,
                                List<String> tabTitleList, List<Fragment> fragments) {
        super(fm);

        mTabTitleList = tabTitleList;
        mFragments = fragments;
    }

    public void setTabTitleList(List<String> tabTitleList) {
        mTabTitleList = tabTitleList;
        mTabCount = tabTitleList.size();
    }

    public void setFragments(List<Fragment> fragments) {
        mFragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mTabCount;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTabTitleList.get(position);
    }

}
