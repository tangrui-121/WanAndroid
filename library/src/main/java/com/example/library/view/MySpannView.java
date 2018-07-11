package com.example.library.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.library.R;
import com.example.library.bean.CS;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mr'Tang on 2018/6/25.
 */

@SuppressLint("AppCompatCustomView")
public class MySpannView extends TextView {
    private Context mContext;
    private PopupWindow mPopupWindow;
    private MyAdapter mAdapter;
    private OnCheckItemListener mListener;

    public MySpannView(Context context) {
        super(context);
        mContext = context;
        initPopWindow();
    }

    public MySpannView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initPopWindow();
    }

    public MySpannView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initPopWindow();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mAdapter == null || mAdapter.getCount() < 1) {
            return true;
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!mPopupWindow.isShowing()) {
                    showPopWindow();
                } else {
                    dismissPopWindow();
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    private void initPopWindow() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.spanner_pop_layout, null);
        if (mPopupWindow == null) {
            mPopupWindow = new PopupWindow(mContext);
            mPopupWindow.setContentView(view);
            mPopupWindow.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
            mPopupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
            mPopupWindow.setFocusable(true);
            mPopupWindow.setOutsideTouchable(true);
            ColorDrawable dw = new ColorDrawable(0x00);
            mPopupWindow.setBackgroundDrawable(dw);
            ListView listView = (ListView) view.findViewById(R.id.listview);
            mAdapter = new MyAdapter();
            listView.setAdapter(mAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (mListener != null) {
                        mListener.checkItem(MySpannView.this, mAdapter.getItem(position), position);
                    }
                    if (mAdapter.getItem(position) instanceof CS) {
                        setText(((CS) mAdapter.getItem(position)).getCsname());
                    }
                    dismissPopWindow();
                }
            });
            mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    setTextImage(R.drawable.icon_down);
                }
            });
        }
    }

    public void setListDatas(List<CS> datas) {
        if (mAdapter != null && datas != null) {
            mAdapter.setDatas(datas);
        }
        mPopupWindow.update();
    }

    public void setOnCheckItemListener(OnCheckItemListener listener) {
        mListener = listener;
    }

    private void showPopWindow() {
        mPopupWindow.setWidth(getWidth());
        mPopupWindow.setHeight(400);
        setTextImage(R.drawable.icon_up);
        mPopupWindow.showAsDropDown(this);
    }

    private void dismissPopWindow() {
        mPopupWindow.dismiss();
    }

    /*
    * 给TextView右边设置图片
    * @param resId
    */
    private void setTextImage(int resId) {
        Drawable drawable = getResources().getDrawable(resId);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());// 必须设置图片大小，否则不显示
        setCompoundDrawables(null, null, drawable, null);
    }

    private class MyAdapter extends BaseAdapter {
        private List<Object> list;

        private MyAdapter() {
            list = new ArrayList<>();
        }

        @Override
        public int getCount() {
            return list.size();
        }

        public void setDatas(List<CS> datas) {
            list.clear();
            list.addAll(datas);
            notifyDataSetChanged();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            TextView tvName;
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.spinner_item_layout, null);
                tvName = (TextView) convertView.findViewById(R.id.spanner_name);
                convertView.setTag(tvName);
            } else {
                tvName = (TextView) convertView.getTag();
            }
            if (getItem(position) instanceof CS) {
                tvName.setText(((CS) getItem(position)).getCsname());
            }
            return convertView;
        }
    }

    public interface OnCheckItemListener {
        void checkItem(View view, Object obj, int position);
    }
}

