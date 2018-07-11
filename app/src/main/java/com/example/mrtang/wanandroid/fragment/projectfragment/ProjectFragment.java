package com.example.mrtang.wanandroid.fragment.projectfragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.example.library.base.AbstractFragment;
import com.example.library.bean.NavigationChildBean;
import com.example.mrtang.wanandroid.R;
import com.example.mrtang.wanandroid.fragment.projectfragment.projectlist.MyPagerAdapter1;
import com.example.mrtang.wanandroid.fragment.projectfragment.projectlist.ProjectlistFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Mr'Tang on 2018/4/24.
 */

public class ProjectFragment<T extends IProjectPresenter> extends AbstractFragment implements IProjectView {

    private IProjectPresenter mPresenter;
    @BindView(R.id._toolbar)
    Toolbar mToolbar;
    @BindView(R.id.knowdetail_tab)
    TabLayout knowdetail_tab;
    @BindView(R.id.knowdetail_viewpager)
    ViewPager knowdetail_viewpager;

    private ArrayList<ProjectlistFragment> mFragmentlist = new ArrayList<ProjectlistFragment>();


    public static ProjectFragment instance(int titles) {
        ProjectFragment fragment = new ProjectFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(TITLE_KEY, titles);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_project;
    }

    @Override
    protected void initPresenter() {
        mPresenter = new ProjectFragmentlmpl(this);
    }

    @Override
    protected void initViews() {
        ButterKnife.bind(getActivity());
    }

    @Override
    protected void updateViews(boolean isRefresh) {
        initView();
        mPresenter.getProjectData(getActivity());
    }

    private void initView() {
        int title = getArguments().getInt(TITLE_KEY);
        initToolBar(mToolbar, false, title);
    }

    public void initData(List<NavigationChildBean> mylist) {
        //设置焦点
        knowdetail_viewpager.setFocusable(false);
        //设置预加载界面数
        knowdetail_viewpager.setOffscreenPageLimit(1);
        for (int a = 0; a < mylist.size(); a++) {
            mFragmentlist.add(ProjectlistFragment.create(mylist.get(a).getId()));
        }
        MyPagerAdapter1 myPagerAdapter = new MyPagerAdapter1(getActivity().getSupportFragmentManager());
        myPagerAdapter.setFragments(mFragmentlist);
        knowdetail_viewpager.setAdapter(myPagerAdapter);
        knowdetail_tab.setupWithViewPager(knowdetail_viewpager);
        for (int a = 0; a < mylist.size(); a++) {
            knowdetail_tab.getTabAt(a).setText(mylist.get(a).getTitle());
        }
    }

    @Override
    public void showError(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

}
