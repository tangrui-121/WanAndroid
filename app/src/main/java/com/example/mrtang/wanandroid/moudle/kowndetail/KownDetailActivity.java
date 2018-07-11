package com.example.mrtang.wanandroid.moudle.kowndetail;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.widget.Toast;

import com.example.library.base.AbstractFragment;
import com.example.library.base.BasicTitleCompatActivity;
import com.example.library.bean.NavigationGroupBean;
import com.example.mrtang.wanandroid.R;
import com.example.mrtang.wanandroid.fragment.knowledgefragment.knowlist.KnowlistFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Mr'Tang on 2018/5/15.
 */

public class KownDetailActivity extends BasicTitleCompatActivity implements IKownDetailView, AbstractFragment.OnFragmentInteractionListener {
    private IKownDetailPresenter mPresenter;
    private static final String KEY_BEAN = "bean";

    @BindView(R.id.knowdetail_tab)
    TabLayout knowdetail_tab;
    @BindView(R.id.knowdetail_viewpager)
    ViewPager knowdetail_viewpager;

    private NavigationGroupBean groupBean;
    private ArrayList<KnowlistFragment> mFragmentlist = new ArrayList<KnowlistFragment>();

    public static void launch(Context context, NavigationGroupBean groupBean) {
        Intent intent = new Intent(context, KownDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_BEAN, groupBean);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected void initPresenter() {
        mPresenter = new KownDetailPresenterlmpl(this);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_kowndetail;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
    }

    @Override
    protected void updateViews(boolean isRefresh) {
        //设置焦点
        knowdetail_viewpager.setFocusable(false);
        //设置预加载界面数
        knowdetail_viewpager.setOffscreenPageLimit(1);
        groupBean = (NavigationGroupBean) getIntent().getSerializableExtra(KEY_BEAN);
        setTitle(groupBean.getName());
        for (int a = 0; a < groupBean.getList().size(); a++) {
            mFragmentlist.add(KnowlistFragment.create(groupBean.getList().get(a).getId()));
        }
        MyPagerAdapter myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        myPagerAdapter.setFragments(mFragmentlist);
        knowdetail_viewpager.setAdapter(myPagerAdapter);
        knowdetail_tab.setupWithViewPager(knowdetail_viewpager);
        for (int a = 0; a < groupBean.getList().size(); a++) {
            knowdetail_tab.getTabAt(a).setText(groupBean.getList().get(a).getTitle());
        }
    }

    @Override
    public void showError(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
