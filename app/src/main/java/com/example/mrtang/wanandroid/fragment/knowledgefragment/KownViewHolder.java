package com.example.mrtang.wanandroid.fragment.knowledgefragment;

import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.example.mrtang.wanandroid.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Mr'Tang on 2018/5/15.
 */

public class KownViewHolder extends BaseViewHolder{
    @BindView(R.id.system_title)
    TextView system_title;
    @BindView(R.id.system_child)
    TextView system_child;

    public KownViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
