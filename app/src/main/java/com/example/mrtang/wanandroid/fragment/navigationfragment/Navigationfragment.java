package com.example.mrtang.wanandroid.fragment.navigationfragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.library.base.BaseFragment;
import com.example.library.bean.NavigationGroupBean;
import com.example.library.utils.CommonUtils;
import com.example.mrtang.wanandroid.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Mr'Tang on 2018/5/10.
 */

public class Navigationfragment<T extends INavigationPresenter> extends BaseFragment implements INavigationView {
    @BindView(R.id._toolbar)
    Toolbar mToolbar;
    @BindView(R.id.navigation_group_left)
    RecyclerView navigation_group_left;
    @BindView(R.id.navigation_group_right)
    RecyclerView navigation_group_right;
    @BindView(R.id.to_top)
    ImageView to_top;

    private INavigationPresenter mPresenter;
    private NavigationGroupAdapter groupAdapter;
    private NavigationChildAdapter childAdapter;
    private List<NavigationGroupBean> navigationGroupBeans = new ArrayList<>();

    public static Navigationfragment instance(int titles) {
        Navigationfragment fragment = new Navigationfragment();
        Bundle bundle = new Bundle();
        bundle.putInt(TITLE_KEY, titles);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_navigation;
    }

    @Override
    protected void initPresenter() {
        mPresenter = new Navigationfragmentlmpl(this);
    }

    @Override
    protected void initViews() {
        ButterKnife.bind(getActivity());
    }

    @Override
    protected void updateViews(boolean isRefresh) {
        super.updateViews(isRefresh);
        initview();
        mPresenter.getNavigationData(getActivity());
    }

    private void initview() {
        int title = getArguments().getInt(TITLE_KEY);
        initToolBar(mToolbar, false, title);

        navigation_group_right.addOnScrollListener(new RecyclerView.OnScrollListener() {
            int mScrollThreshold;

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                boolean isSignificantDelta = Math.abs(dy) > mScrollThreshold;
                if (isSignificantDelta) {
                    if (dy > 0) {
                        to_top.setVisibility(View.GONE);
                    } else {
                        to_top.setVisibility(View.VISIBLE);
                    }
                }
            }

            public void setScrollThreshold(int scrollThreshold) {
                mScrollThreshold = scrollThreshold;
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                if (layoutManager instanceof LinearLayoutManager) {
                    LinearLayoutManager linearManager = (LinearLayoutManager) layoutManager;
                    //获取最后一个可见view的位置
                    int lastItemPosition = linearManager.findLastVisibleItemPosition();
                    //获取第一个可见view的位置
                    int firstItemPosition = linearManager.findFirstVisibleItemPosition();
                    smoothMoveToPosition(navigation_group_left, firstItemPosition);
                    if (firstItemPosition == 0) {
                        to_top.setVisibility(View.GONE);
                    }
                }
            }
        });


        to_top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                smoothMoveToPosition(navigation_group_right, 0);
                groupAdapter.changeSelected(0);
            }
        });

        if (CommonUtils.isNetworkConnected()) {
            showLoading();
        }
    }

    public void initData(final List<NavigationGroupBean> mylist) {
        showNormal();
        groupAdapter = new NavigationGroupAdapter(R.layout.item_navigation_group, mylist);
        navigation_group_left.setLayoutManager(new LinearLayoutManager(getActivity()));
        groupAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                smoothMoveToPosition(navigation_group_right, position);
                groupAdapter.changeSelected(position);
            }
        });
        navigation_group_left.setAdapter(groupAdapter);

        childAdapter = new NavigationChildAdapter(R.layout.item_navigation_child, mylist);
        navigation_group_right.setLayoutManager(new LinearLayoutManager(getActivity()));
        navigation_group_right.setAdapter(childAdapter);
    }

    @Override
    public void showError(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    //目标项是否在最后一个可见项之后
    private boolean mShouldScroll;
    //记录目标项位置
    private int mToPosition;

    /**
     * 滑动到指定位置
     */
    private void smoothMoveToPosition(RecyclerView mRecyclerView, final int position) {
        // 第一个可见位置
        int firstItem = mRecyclerView.getChildLayoutPosition(mRecyclerView.getChildAt(0));
        // 最后一个可见位置
        int lastItem = mRecyclerView.getChildLayoutPosition(mRecyclerView.getChildAt(mRecyclerView.getChildCount() - 1));
        if (position < firstItem) {
            // 第一种可能:跳转位置在第一个可见位置之前
            mRecyclerView.smoothScrollToPosition(position);
        } else if (position <= lastItem) {
            // 第二种可能:跳转位置在第一个可见位置之后
            int movePosition = position - firstItem;
            if (movePosition >= 0 && movePosition < mRecyclerView.getChildCount()) {
                int top = mRecyclerView.getChildAt(movePosition).getTop();
                mRecyclerView.smoothScrollBy(0, top);
            }
        } else {
            // 第三种可能:跳转位置在最后可见项之后
            mRecyclerView.smoothScrollToPosition(position);
            mToPosition = position;
            mShouldScroll = true;
        }
    }
}
