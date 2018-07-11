package com.example.mrtang.wanandroid.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.library.bean.DoclistBean;
import com.example.mrtang.wanandroid.R;
import com.example.mrtang.wanandroid.fragment.homefragment.KnowledgeHierarchyListViewHolder;
import com.squareup.picasso.Picasso;
import java.util.List;

/**
 * Created by Mr'Tang on 2018/4/27.
 */

public class HomeDocAdapter extends BaseQuickAdapter<DoclistBean, KnowledgeHierarchyListViewHolder> {

    public HomeDocAdapter(int layoutResId, @Nullable List<DoclistBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(KnowledgeHierarchyListViewHolder helper, DoclistBean doclistBean) {
        if (!doclistBean.getEnvelopePic().isEmpty()) {
            Picasso.with(mContext)
                    .load(doclistBean.getEnvelopePic())
                    .into((ImageView) helper.getView(R.id.doc_authorimg));
        }
        helper.setText(R.id.doc_author, doclistBean.getAuthor());
        helper.setText(R.id.doc_time, doclistBean.getNiceDate());
        helper.setText(R.id.doc_title, doclistBean.getTitle().length() <= 30 ? doclistBean.getTitle() : doclistBean.getTitle().substring(0, 29) + "...");
        helper.setText(R.id.doc_desc, doclistBean.getChapterName());
        helper.setImageResource(R.id.doc_zan, doclistBean.getCollect() ? R.mipmap.had_zan : R.mipmap.no_zan);
        helper.addOnClickListener(R.id.doc_zan);
    }
}