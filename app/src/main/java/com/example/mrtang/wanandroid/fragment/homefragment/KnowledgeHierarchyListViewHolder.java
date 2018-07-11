package com.example.mrtang.wanandroid.fragment.homefragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.mrtang.wanandroid.R;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Mr'Tang on 2018/4/27.
 */

public class KnowledgeHierarchyListViewHolder extends BaseViewHolder {
    @BindView(R.id.doc_author)
    TextView doc_author;
    @BindView(R.id.doc_time)
    TextView doc_time;
    @BindView(R.id.doc_title)
    TextView doc_title;
    @BindView(R.id.doc_desc)
    TextView doc_desc;
    @BindView(R.id.doc_zan)
    ImageView doc_zan;
    @BindView(R.id.doc_timeimg)
    ImageView doc_timeimg;

    public KnowledgeHierarchyListViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}

