package com.example.mrtang.wanandroid.fragment.knowledgefragment;

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
import com.example.mrtang.wanandroid.fragment.navigationfragment.NavigationGroupAdapter;
import com.example.mrtang.wanandroid.moudle.kowndetail.KownDetailActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Mr'Tang on 2018/4/24.
 */

public class KownFragment<T extends IKownPresenter> extends BaseFragment implements IKownView {

    private IKownPresenter mPresenter;
    @BindView(R.id._toolbar)
    Toolbar mToolbar;
    @BindView(R.id.refreshLayout)
    RecyclerView kown_recyclerview;
    @BindView(R.id.to_top)
    ImageView to_top;

    private KownAdapter groupAdapter;

    public static KownFragment instance(int titles) {
        KownFragment fragment = new KownFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(TITLE_KEY, titles);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_kownledge;
    }

    @Override
    protected void initPresenter() {
        mPresenter = new KownFragmentlmpl(this);
    }

    @Override
    protected void initViews() {
        ButterKnife.bind(getActivity());
    }

    @Override
    protected void updateViews(boolean isRefresh) {
        super.updateViews(isRefresh);
        initView();
        mPresenter.getKownData(getActivity());
    }

    private void initView() {
        int title = getArguments().getInt(TITLE_KEY);
        initToolBar(mToolbar, false, title);

        kown_recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                    if (firstItemPosition == 0) {
                        to_top.setVisibility(View.GONE);
                    }
                }
            }
        });

        to_top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                smoothMoveToPosition(kown_recyclerview, 0);
            }
        });

        if (CommonUtils.isNetworkConnected()) {
            showLoading();
        }
    }

    public void initData(final List<NavigationGroupBean> mylist) {
        showNormal();
        groupAdapter = new KownAdapter(R.layout.item_home_kown, mylist);
        kown_recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        groupAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                KownDetailActivity.launch(getActivity(), mylist.get(position));
            }
        });
        kown_recyclerview.setAdapter(groupAdapter);
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
