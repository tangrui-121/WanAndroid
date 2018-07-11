package com.example.mrtang.wanandroid.fragment.knowledgefragment.knowlist;

import android.app.ActivityOptions;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.library.base.BaseFragment;
import com.example.library.bean.DoclistBean;
import com.example.library.okhttp.CollectEvent;
import com.example.library.utils.CommonUtils;
import com.example.mrtang.wanandroid.R;
import com.example.mrtang.wanandroid.adapter.HomeDocAdapter;
import com.example.mrtang.wanandroid.moudle.webview.CommonWebviewActivity;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Mr'Tang on 2018/5/16.
 */

public class KnowlistFragment<T extends IKnowlistPresenter> extends BaseFragment implements IKnowlistView {
    private static final String KEY_ID = "child_id";
    @BindView(R.id.refreshLayout)
    RefreshLayout refreshLayout;
    @BindView(R.id.main_pager_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.to_top)
    ImageView to_top;

    private HomeDocAdapter mAdapter;
    private IKnowlistPresenter mPresenter;
    private List<DoclistBean> doclistBeans = new ArrayList<>();
    private int page = 0;
    private int cid;

    public static KnowlistFragment create(int id) {
        KnowlistFragment f = new KnowlistFragment();
        Bundle args = new Bundle();
        args.putInt(KEY_ID, id);
        f.setArguments(args);
        return f;
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_viewpager;
    }

    @Override
    protected void initPresenter() {
        mPresenter = new KnowlistFragmentlmpl(this);
    }

    @Override
    protected void initViews() {
        ButterKnife.bind(getActivity());
    }

    @Override
    protected void updateViews(boolean isRefresh) {
        super.updateViews(isRefresh);
        cid = getArguments().getInt(KEY_ID, 0);
        if (cid == 0) {
            return;
        }
        initview();
    }

    @Override
    public void collectArticleSuccess(int position, DoclistBean bean) {
        mAdapter.setData(position, bean);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(CollectEvent event) {
        mAdapter.setData(event.getPosition(), event.getDoclistBean());
    }

    private void initview() {
        EventBus.getDefault().register(this);
        mAdapter = new HomeDocAdapter(R.layout.item_homedoc, doclistBeans);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                DoclistBean doclistBean = doclistBeans.get(position);
                CommonWebviewActivity.lunchActivity(getActivity(), null, position, doclistBean, true, 0);
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
        mRecyclerView.setAdapter(mAdapter);
        setRefresh();
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
        if (CommonUtils.isNetworkConnected()) {
            showLoading();
        }

        mPresenter.getKnowlistData(getActivity(), cid, page);
    }

    public void initData(List<DoclistBean> doclistBeans, boolean is_firstpage) {
        showNormal();
        if (is_firstpage) {
            mAdapter.replaceData(doclistBeans);
        } else {
            mAdapter.addData(doclistBeans);
        }
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
                mPresenter.getKnowlistData(getActivity(), cid, page);
                refreshLayout.finishRefresh(1000);
            }
        });
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                page++;
                mPresenter.getKnowlistData(getActivity(), cid, page);
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
