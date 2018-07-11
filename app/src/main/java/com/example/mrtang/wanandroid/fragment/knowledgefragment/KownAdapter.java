package com.example.mrtang.wanandroid.fragment.knowledgefragment;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.library.bean.NavigationChildBean;
import com.example.library.bean.NavigationGroupBean;
import com.example.library.utils.CommonUtils;
import com.example.mrtang.wanandroid.R;
import com.example.mrtang.wanandroid.fragment.navigationfragment.NavigationChildViewHolder;

import java.util.List;

/**
 * Created by Mr'Tang on 2018/5/15.
 */

public class KownAdapter extends BaseQuickAdapter<NavigationGroupBean, KownViewHolder> {
    public KownAdapter(int layoutResId, @Nullable List<NavigationGroupBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(KownViewHolder helper, NavigationGroupBean item) {
        helper.setText(R.id.system_title, item.getName());
        helper.setTextColor(R.id.system_title, CommonUtils.randomColor());
        String child_title = new String();
        for (NavigationChildBean childBean : item.getList()) {
            child_title = child_title + childBean.getTitle() + "   ";
        }
        helper.setText(R.id.system_child, child_title);
    }
}
