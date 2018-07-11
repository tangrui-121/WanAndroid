package com.example.mrtang.wanandroid;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.support.v4.app.Fragment;
import android.widget.TextView;
import android.widget.Toast;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.example.girl.module.grilhome.GrilHomeActivity;
import com.example.library.base.BasicCompatActivity;
import com.example.library.dao.DataBaseTool;
import com.example.library.utils.DisplayUtils;
import com.example.library.utils.SystemTool;
import com.example.library.utils.T;
import com.example.library.view.CommonDialog;
import com.example.mrtang.wanandroid.adapter.MainFragmentPagerAdapter;
import com.example.mrtang.wanandroid.moudle.aboutus.AboutusActivity;
import com.example.mrtang.wanandroid.moudle.collect.CollectActivity;
import com.example.mrtang.wanandroid.moudle.login.LoginActivity;
import com.example.mrtang.wanandroid.moudle.set.SetActivity;

import java.util.List;

/**
 * Created by Mr'Tang on 2018/4/24.
 */

public class AppMainActivity extends BasicCompatActivity<IAppMainPresenter> implements IAppMainView, BottomNavigationBar.OnTabSelectedListener {

    FrameLayout fragmentContent;
    BottomNavigationBar navigationBar;
    IAppMainPresenter mPresenter;
    MainFragmentPagerAdapter fragmentAdapter;

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private TextView login_name;
    private TextView person_introduce;


    @Override
    protected void initPresenter() {
        mPresenter = new AppMainPresenterImpl(this);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        DisplayUtils.initScreen(this);
        fragmentContent = (FrameLayout) findViewById(R.id.fragment_content);
        navigationBar = (BottomNavigationBar) findViewById(R.id.activity_main_bottombar);
        navigationBar.setMode(BottomNavigationBar.MODE_FIXED);
        navigationBar
                .setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC
                );
        navigationBar.setBackgroundResource(R.drawable.navigationbar_bg);
        navigationBar.setInActiveColor(R.color.gray);
        navigationBar.addItem(new BottomNavigationItem(R.mipmap.tab_home, R.string.main_title_home).setActiveColorResource(R.color.tab_txt_color))
                .addItem(new BottomNavigationItem(R.mipmap.tab_knowledge, R.string.main_title_knowledge).setActiveColorResource(R.color.tab_txt_color))
                .addItem(new BottomNavigationItem(R.mipmap.tab_navigation, R.string.main_title_navigation).setActiveColorResource(R.color.tab_txt_color))
                .addItem(new BottomNavigationItem(R.mipmap.tab_project, R.string.main_title_project).setActiveColorResource(R.color.tab_txt_color))
                .setFirstSelectedPosition(0)
                .initialise();
        navigationBar.setTabSelectedListener(this);

        initNavigationView();
    }

    @Override
    protected void updateViews(boolean isRefresh) {
        mPresenter.initFragments();
    }

    @Override
    public void initFragment(List<Fragment> fragments) {
        fragmentAdapter = new MainFragmentPagerAdapter(getSupportFragmentManager(), fragments);
        setDefaultFragment(0);
    }

    @Override
    public void setDefaultFragment(int position) {
        showFragment(position);
    }

    @Override
    public void showFragment(int position) {
        Fragment fragment = (Fragment) fragmentAdapter
                .instantiateItem(fragmentContent, position);
        fragmentAdapter.setPrimaryItem(fragmentContent, 0, fragment);
        fragmentAdapter.finishUpdate(fragmentContent);
    }

    @Override
    public void updateMessNum(int num) {

    }

    @Override
    public void onTabSelected(int position) {
        mPresenter.onCheckedFragment(position);
    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {
    }

    @Override
    public void onBackPressed() {
        handleExitTip();
    }

    private boolean isOnKeyBacking;
    private Handler mMainLoopHandler = new Handler(Looper.getMainLooper());
    private Runnable onBackTimeRunnable = new Runnable() {
        @Override
        public void run() {
            isOnKeyBacking = false;
        }
    };

    private boolean handleExitTip() {
        if (isOnKeyBacking) {
            mMainLoopHandler.removeCallbacks(onBackTimeRunnable);
            isOnKeyBacking = false;
            SystemTool.AppExit(this);
        } else {
            isOnKeyBacking = true;
            mMainLoopHandler.postDelayed(onBackTimeRunnable, 2000);
            showExitTips();
            return true;
        }
        return false;
    }

    public void showExitTips() {
        T.showShort(this, R.string.basic_back_tips);
    }

    private void initNavigationView() {
        drawerLayout = (DrawerLayout) findViewById(R.id.activity_na);
        navigationView = (NavigationView) findViewById(R.id.nav);
        login_name = navigationView.getHeaderView(0).findViewById(R.id.login_name);
        login_name.setText(DataBaseTool.SearchUserInfo().getUsername());
        person_introduce = navigationView.getHeaderView(0).findViewById(R.id.person_introduce);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, 0, 0) {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                //获取mDrawerLayout中的第一个子布局，也就是布局中的RelativeLayout
                //获取抽屉的view
                View mContent = drawerLayout.getChildAt(0);
                float scale = 1 - slideOffset;
                float endScale = 0.8f + scale * 0.2f;
                float startScale = 1 - 0.3f * scale;

                //设置左边菜单滑动后的占据屏幕大小
                drawerView.setScaleX(startScale);
                drawerView.setScaleY(startScale);
                //设置菜单透明度
                drawerView.setAlpha(0.6f + 0.4f * (1 - scale));

                //设置内容界面水平和垂直方向偏转量
                //在滑动时内容界面的宽度为 屏幕宽度减去菜单界面所占宽度
                mContent.setTranslationX(drawerView.getMeasuredWidth() * (1 - scale));
                //设置内容界面操作无效（比如有button就会点击无效）
                mContent.invalidate();
                //设置右边菜单滑动后的占据屏幕大小
                mContent.setScaleX(endScale);
                mContent.setScaleY(endScale);
            }
        };
        toggle.syncState();
        drawerLayout.addDrawerListener(toggle);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.sideslip_wanandroid:
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.sideslip_collect:
                        CollectActivity.launch(AppMainActivity.this);
                        break;
                    case R.id.sideslip_aboutus:
//                        AboutusActivity.launch(AppMainActivity.this);


                        break;
                    case R.id.sideslip_set:
                        SetActivity.launch(AppMainActivity.this);
                        break;
                    case R.id.sideslip_signout:
                        final CommonDialog dialog = new CommonDialog(AppMainActivity.this, 4, "温馨提示", "退出登录?", "否", "是");
                        dialog.setOkListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                DataBaseTool.deleteUserInfo(DataBaseTool.SearchUserInfo().getId());
                                startActivity(new Intent(AppMainActivity.this, LoginActivity.class));
                                finish();
                            }
                        });
                        dialog.show();
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public void showError(String msg) {

    }
}
