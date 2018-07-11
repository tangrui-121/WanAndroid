package com.example.mrtang.wanandroid.fragment.homefragment;

import android.app.ActivityOptions;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.library.base.BaseFragment;
import com.example.library.bean.BannerBean;
import com.example.library.bean.DoclistBean;
import com.example.library.okhttp.CollectEvent;
import com.example.library.utils.CommonUtils;
import com.example.library.utils.LogUtils;
import com.example.mrtang.wanandroid.R;
import com.example.mrtang.wanandroid.adapter.HomeDocAdapter;
import com.example.mrtang.wanandroid.moudle.webview.CommonWebviewActivity;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Mr'Tang on 2018/4/24.
 */

public class HomeFragment<T extends IHomePresenter> extends BaseFragment implements IHomeView {

    Banner bannerM;
    @BindView(R.id._toolbar)
    Toolbar mToolbar;
    @BindView(R.id.refreshLayout)
    RefreshLayout refreshLayout;
    @BindView(R.id.main_pager_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.to_top)
    ImageView to_top;

    private List<String> mBannerTitleList;
    private List<String> mBannerUrlList;

    private List<DoclistBean> doclistBeans = new ArrayList<>();
    private HomeDocAdapter mAdapter;
    private IHomePresenter mPresenter;
    private int page = 0;

    public static HomeFragment instance(int titles) {
        HomeFragment fragment = new HomeFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(TITLE_KEY, titles);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initPresenter() {
        mPresenter = new HomeFragmentlmpl(this);
    }

    @Override
    protected void initViews() {
        ButterKnife.bind(getActivity());
    }

    @Override
    protected void updateViews(boolean isRefresh) {
        super.updateViews(isRefresh);
        initview();
        mPresenter.rxjava_zip(page);
    }


    private void initview() {
        EventBus.getDefault().register(this);
        int title = getArguments().getInt(TITLE_KEY);
        initToolBar(mToolbar, false, title);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                smoothMoveToPosition(mRecyclerView, 0);
            }
        });

        mAdapter = new HomeDocAdapter(R.layout.item_homedoc, doclistBeans);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                DoclistBean doclistBean = doclistBeans.get(position);
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(getActivity(), mRecyclerView, getString(R.string.share_view));
                CommonWebviewActivity.lunchActivity(getActivity(), options, position, doclistBean, true, 0);
            }
        });
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.doc_zan:
                        if (mAdapter.getItem(position).getCollect()) {
                            mPresenter.uncollectDoc(position, mAdapter.getItem(position));
                        } else {
                            mPresenter.collectDoc(position, mAdapter.getItem(position));
                        }
                        break;
                    default:
                        break;
                }
            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        LinearLayout mHeaderGroup = ((LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.head_head, null));
        bannerM = mHeaderGroup.findViewById(R.id.my_banner);
        mHeaderGroup.removeView(bannerM);
        mAdapter.addHeaderView(bannerM);
        mRecyclerView.setAdapter(mAdapter);
        setRefresh();

        if (CommonUtils.isNetworkConnected()) {
            showLoading();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(CollectEvent event) {
        mAdapter.setData(event.getPosition(), event.getDoclistBean());
    }

    @Override
    public void collectArticleSuccess(int position, DoclistBean bean) {
        mAdapter.setData(position, bean);
    }


    public void initData(List<DoclistBean> doclistBeans, boolean is_firstpage) {
        if (is_firstpage) {
            mAdapter.replaceData(doclistBeans);
        } else {
            mAdapter.addData(doclistBeans);
        }
    }

    public void initBanner(final List<BannerBean> mylist) {
        showNormal();
        mBannerTitleList = new ArrayList<>();
        List<String> bannerImageList = new ArrayList<>();
        mBannerUrlList = new ArrayList<>();
        for (BannerBean bannerData : mylist) {
            mBannerTitleList.add(bannerData.getTitle());
            bannerImageList.add(bannerData.getImagePath());
            mBannerUrlList.add(bannerData.getUrl());
        }
        //设置banner样式
        bannerM.setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE);
        //设置图片加载器
        bannerM.setImageLoader(new ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                Picasso.with(mContext)
                        .load(path.toString())
                        .placeholder(null)
                        .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                        .into(imageView);
            }
        });
        //设置图片集合
        bannerM.setImages(bannerImageList);
        //设置banner动画效果
        bannerM.setBannerAnimation(Transformer.DepthPage);
        //设置标题集合（当banner样式有显示title时）
        bannerM.setBannerTitles(mBannerTitleList);
        //设置自动轮播，默认为true
        bannerM.isAutoPlay(true);
        //设置轮播时间
        bannerM.setDelayTime(mylist.size() * 400);
        //设置指示器位置（当banner模式中有指示器时）
        bannerM.setIndicatorGravity(BannerConfig.CENTER);
        bannerM.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                BannerBean bannerBean = mylist.get(position);
                DoclistBean doclistBean = new DoclistBean();
                doclistBean.setCollect(false);
                doclistBean.setTitle(bannerBean.getTitle());
                doclistBean.setLink(bannerBean.getUrl());
                CommonWebviewActivity.lunchActivity(getActivity(), null, position, doclistBean, false, 0);
            }
        });
        //banner设置方法全部调用完毕时最后调用
        bannerM.start();
    }

    @Override
    public void showError(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    private void setRefresh() {
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                page = 0;
                mPresenter.rxjava_zip(page);
                refreshLayout.finishRefresh(1000);
            }
        });
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                page++;
                mPresenter.rxjava_zip(page);
                refreshLayout.finishLoadmore(1000);
            }
        });
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

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
