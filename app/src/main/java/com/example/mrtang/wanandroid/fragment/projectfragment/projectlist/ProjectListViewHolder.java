package com.example.mrtang.wanandroid.fragment.projectfragment.projectlist;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.example.mrtang.wanandroid.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Mr'Tang on 2018/6/7.
 */

public class ProjectListViewHolder extends BaseViewHolder {
    @BindView(R.id.item_project_title)
    TextView item_project_title;
    @BindView(R.id.item_project_desc)
    TextView item_project_desc;
    @BindView(R.id.item_project_time)
    TextView item_project_time;
    @BindView(R.id.item_project_author)
    TextView item_project_author;
    @BindView(R.id.item_project_img)
    ImageView item_project_img;

    public ProjectListViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
