package com.example.mrtang.wanandroid.fragment.homefragment;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.library.bean.DoclistBean;
import com.example.mrtang.wanandroid.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Mr'Tang on 2018/4/25.
 */

public class HomeDocAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {
    private List<DoclistBean> mData;
    private Context mContext;
    private OnItemClickListener mOnItemClickListener = null;

    public HomeDocAdapter(Context context, List<DoclistBean> data) {
        this.mContext = context;
        this.mData = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_homedoc, null);
        view.setOnClickListener(this);
        return new TestViewHolder(view);
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(v, (int) v.getTag());
        }
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        TestViewHolder tHolder = (TestViewHolder) holder;
        tHolder.itemView.setTag(position);
        DoclistBean doclistBean = mData.get(position);

        tHolder.doc_author.setText(doclistBean.getAuthor());
        tHolder.doc_time.setText(doclistBean.getNiceDate());
        tHolder.doc_title.setText(doclistBean.getTitle().length() <= 30 ? doclistBean.getTitle() : doclistBean.getTitle().substring(0, 29) + "...");
        tHolder.doc_desc.setText(doclistBean.getChapterName());
        tHolder.doc_zan.setBackgroundResource(doclistBean.getZan() == 0 ? R.mipmap.no_zan : R.mipmap.had_zan);

    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    private class TestViewHolder extends RecyclerView.ViewHolder {
        CircleImageView doc_authorimg;
        TextView doc_author;
        TextView doc_time;
        TextView doc_title;
        TextView doc_desc;
        ImageView doc_zan;

        public TestViewHolder(View itemView) {
            super(itemView);
            doc_authorimg = itemView.findViewById(R.id.doc_authorimg);
            doc_author = itemView.findViewById(R.id.doc_author);
            doc_time = itemView.findViewById(R.id.doc_time);
            doc_title = itemView.findViewById(R.id.doc_title);
            doc_desc = itemView.findViewById(R.id.doc_desc);
            doc_zan = itemView.findViewById(R.id.doc_zan);
        }
    }

    public static interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

}
