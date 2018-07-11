package com.example.mrtang.wanandroid.fragment.navigationfragment;

import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.library.bean.DoclistBean;
import com.example.library.bean.NavigationChildBean;
import com.example.library.bean.NavigationGroupBean;
import com.example.library.utils.CommonUtils;
import com.example.mrtang.wanandroid.R;
import com.example.mrtang.wanandroid.moudle.webview.CommonWebviewActivity;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;

/**
 * Created by Mr'Tang on 2018/5/14.
 */

public class NavigationChildAdapter extends BaseQuickAdapter<NavigationGroupBean, NavigationChildViewHolder> {
    public NavigationChildAdapter(int layoutResId, @Nullable List<NavigationGroupBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(NavigationChildViewHolder helper, final NavigationGroupBean item) {
        helper.setText(R.id.group_name, item.getName());
        final TagFlowLayout mTagFlowLayout = helper.getView(R.id.child_flowlayout);
        helper.child_flowlayout.setAdapter(new TagAdapter<NavigationChildBean>(item.getList()) {
            @Override
            public View getView(FlowLayout parent, int position, final NavigationChildBean navigationChildBean) {
                TextView tv = (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.flow_layout_tv,
                        mTagFlowLayout, false);
                if (navigationChildBean == null) {
                    return null;
                }
                tv.setPadding(CommonUtils.dp2px(10), CommonUtils.dp2px(10),
                        CommonUtils.dp2px(10), CommonUtils.dp2px(10));
                tv.setText(item.getList().get(position).getTitle());
                tv.setTextColor(CommonUtils.randomColor());
                mTagFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
                    @Override
                    public boolean onTagClick(View view, int position, FlowLayout parent) {
                        DoclistBean doclistBean = new DoclistBean();
                        doclistBean.setCollect(false);
                        doclistBean.setTitle(navigationChildBean.getTitle());
                        doclistBean.setLink(item.getList().get(position).getLink());
                        CommonWebviewActivity.lunchActivity(parent.getContext(), null, position, doclistBean, false,0);
                        return true;
                    }
                });
                return tv;
            }
        });
    }
}
