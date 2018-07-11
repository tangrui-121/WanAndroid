package com.example.mrtang.wanandroid.fragment.navigationfragment;

import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.example.mrtang.wanandroid.R;
import com.zhy.view.flowlayout.TagFlowLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Mr'Tang on 2018/5/14.
 */

public class NavigationChildViewHolder extends BaseViewHolder {
    @BindView(R.id.group_name)
    TextView group_name;
    @BindView(R.id.child_flowlayout)
    TagFlowLayout child_flowlayout;

    public NavigationChildViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
