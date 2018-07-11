package com.example.mrtang.wanandroid.fragment.navigationfragment;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.library.bean.NavigationGroupBean;
import com.example.mrtang.wanandroid.R;
import com.example.mrtang.wanandroid.fragment.navigationfragment.NavigationGroupViewHolder;

import java.util.List;

/**
 * Created by Mr'Tang on 2018/5/11.
 */

public class NavigationGroupAdapter extends BaseQuickAdapter<NavigationGroupBean, NavigationGroupViewHolder> {
    public NavigationGroupAdapter(int layoutResId, @Nullable List<NavigationGroupBean> data) {
        super(layoutResId, data);
    }

    int mSelect = 0; // 选中项

    public void changeSelected(int positon) { // 刷新方法
        if (positon != mSelect) {
            mSelect = positon;
            notifyDataSetChanged();
        }
    }

    @SuppressLint("ResourceType")
    @Override
    protected void convert(NavigationGroupViewHolder helper, NavigationGroupBean groupBean) {
        TextView tv = helper.getView(R.id.navigation_groupname);
        LinearLayout lin = helper.getView(R.id.navigation_group);
        tv.setText(groupBean.getName());
        if (mSelect == groupBean.getTag()) {
            tv.setTextColor(Color.parseColor("#009587"));
            lin.setBackgroundColor(Color.parseColor("#ffffff"));
        } else {
            tv.setTextColor(Color.parseColor("#FF757575"));
            lin.setBackgroundColor(Color.parseColor("#e6e6e6"));
        }
    }
}
