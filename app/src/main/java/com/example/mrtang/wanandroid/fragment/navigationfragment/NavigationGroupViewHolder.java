package com.example.mrtang.wanandroid.fragment.navigationfragment;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.example.mrtang.wanandroid.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Mr'Tang on 2018/5/11.
 */

public class NavigationGroupViewHolder extends BaseViewHolder {
    @BindView(R.id.navigation_groupname)
    TextView navigation_groupname;
    @BindView(R.id.navigation_group)
    LinearLayout navigation_group;

    public NavigationGroupViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
