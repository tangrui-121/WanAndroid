package com.example.mrtang.wanandroid.fragment.homefragment;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.library.bean.DoclistBean;
import com.example.library.utils.TimeUtil;
import com.example.mrtang.wanandroid.R;

import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Mr'Tang on 2018/4/26.
 */

public class HomeDoclistAdapter extends BaseAdapter {

    private List<DoclistBean> mylist;
    private Context mContext;
    private OnClickListener onClickListener;

    public HomeDoclistAdapter(Context context, List<DoclistBean> data) {
        this.mContext = context;
        this.mylist = data;
    }

    public void addAll(List<DoclistBean> data, boolean append) {
        if (mylist != null) {
            if (!append) {
                mylist.clear();
            }
            mylist.addAll(data);
        } else {
            mylist = data;
        }
        refreshData();
    }

    public void refreshData() {
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mylist.size();
    }

    @Override
    public Object getItem(int position) {
        return mylist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView,
                        ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_homedoc, parent, false);
        }
        CircleImageView doc_authorimg = convertView.findViewById(R.id.doc_authorimg);
        TextView doc_author = convertView.findViewById(R.id.doc_author);
        TextView doc_time = convertView.findViewById(R.id.doc_time);
        TextView doc_title = convertView.findViewById(R.id.doc_title);
        TextView doc_desc = convertView.findViewById(R.id.doc_desc);
        ImageView doc_zan = convertView.findViewById(R.id.doc_zan);

        DoclistBean doclistBean = mylist.get(position);

        doc_author.setText(doclistBean.getAuthor());
        doc_time.setText(TimeUtil.getTimeFormatText(TimeUtil.stringToDate(doclistBean.getNiceDate(), "yyyy-MM-dd")));
        doc_title.setText(doclistBean.getTitle().length() <= 30 ? doclistBean.getTitle() : doclistBean.getTitle().substring(0, 29) + "...");
        doc_desc.setText(doclistBean.getChapterName());
        doc_zan.setBackgroundResource(doclistBean.getZan() == 0 ? R.mipmap.no_zan : R.mipmap.had_zan);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onItemClick(position);
            }
        });
        return convertView;
    }

    //回调接口
    public interface OnClickListener {
        void onItemClick(int position);
    }

    public void setOnClickListener(OnClickListener onItemActionClick) {
        this.onClickListener = onItemActionClick;
    }
}
