package com.example.tarena.protop.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.tarena.protop.fragment.AndroidFragment;
import com.example.tarena.protop.fragment.IosFragment;
import com.example.tarena.protop.fragment.ToolsFragment;
import com.example.tarena.protop.fragment.WebFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：Linqiang
 * 时间：2016/5/14:11:34
 * 邮箱: linqiang2010@outlook.com
 * 说明：自定义Adapter动态加载fragment
 */
public class MyViewPagerAdapter extends FragmentPagerAdapter {
    List<Fragment> list = new ArrayList<Fragment>() ;

    public MyViewPagerAdapter(FragmentManager fm) {
        super(fm);
        initFragment();
    }
    //初始化Fragment
    private void initFragment() {
        list.add(new AndroidFragment());
        list.add(new IosFragment());
        list.add(new WebFragment());
        list.add(new ToolsFragment());
       // list.add(new QuestionFragment());
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return 4;
    }
}
