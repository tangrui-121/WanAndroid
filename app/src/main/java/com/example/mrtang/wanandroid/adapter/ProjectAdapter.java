package com.example.mrtang.wanandroid.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.library.bean.DoclistBean;
import com.example.mrtang.wanandroid.R;
import com.example.mrtang.wanandroid.fragment.projectfragment.projectlist.ProjectListViewHolder;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Mr'Tang on 2018/6/7.
 */

public class ProjectAdapter extends BaseQuickAdapter<DoclistBean, ProjectListViewHolder> {

    public ProjectAdapter(int layoutResId, @Nullable List<DoclistBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(ProjectListViewHolder helper, DoclistBean doclistBean) {
        if (!doclistBean.getEnvelopePic().isEmpty()) {
            Picasso.with(mContext)
                    .load(doclistBean.getEnvelopePic())
                    .into((ImageView) helper.getView(R.id.item_project_img));
        }
        helper.setText(R.id.item_project_title, doclistBean.getTitle());
        helper.setText(R.id.item_project_desc, doclistBean.getDesc());
        helper.setText(R.id.item_project_time, doclistBean.getNiceDate());
        helper.setText(R.id.item_project_author, doclistBean.getAuthor());
    }
}
