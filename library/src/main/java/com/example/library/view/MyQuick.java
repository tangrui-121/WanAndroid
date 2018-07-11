package com.example.library.view;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.library.R;
import com.example.library.bean.QuickBean;
import com.example.library.utils.ConvertM;
import com.example.library.utils.ToastShow;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mr'Tang on 2018/6/25.
 */

public class MyQuick extends LinearLayout {
    private Context mContext;
    private ViewPager viewpager;
    private LinearLayout tips;
    private List<QuickBean> quickBeans = new ArrayList<>();

    //装载GridView的list
    private List<GridView> grid_list;
    //控制圆点View的形状，未选中的颜色
    private GradientDrawable gradientDrawable;
    //控制圆点View的形状，选中的颜色
    private GradientDrawable gradientDrawableSelected;
    //选中圆点的颜色值，默认为#FF3333
    private int indexColorResId;
    //装载圆点指示器的list
    private List<View> view_list;
    //将原始数据等分后的list集合
    private List<List<QuickBean>> lists;
    private ViewPagerAdapter adapter;

    public MyQuick(Context context) {
        super(context);
        mContext = context;
        initView();
    }

    public MyQuick(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView();
    }

    private void initView() {
        LayoutInflater.from(mContext).inflate(R.layout.view_quick, this, true);
        viewpager = (ViewPager) findViewById(R.id.viewpager);
        tips = (LinearLayout) findViewById(R.id.tips);
    }

    public void setData(List<QuickBean> list) {
        this.quickBeans = list;
        lists = splitList(quickBeans, 10);
    }

    //初始化圆点指示器
    private void initSpot() {
        view_list = new ArrayList<>(lists.size());
        //使用GradientDrawable构造圆形控件
        gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(GradientDrawable.OVAL);
        gradientDrawable.setColor(mContext.getResources().getColor(R.color.text));
        gradientDrawableSelected = new GradientDrawable();
        gradientDrawableSelected.setShape(GradientDrawable.OVAL);
        if (indexColorResId != 0) {
            gradientDrawableSelected.setColor(indexColorResId);
        } else {
            gradientDrawableSelected.setColor(mContext.getResources().getColor(R.color.mainColor));
        }
        for (int i = 0; i < lists.size(); i++) {
            View view = new View(mContext);
            LayoutParams lpView = new LayoutParams(ConvertM.dp2px(mContext, 8), ConvertM.dp2px(mContext, 8));
            lpView.rightMargin = ConvertM.dp2px(mContext, 4);
            view.setLayoutParams(lpView);
            if (0 == i) {
                view.setBackgroundDrawable(gradientDrawableSelected);
            } else {
                view.setBackgroundDrawable(gradientDrawable);
            }
            view.bringToFront();
            view_list.add(view);
            tips.addView(view);
        }
    }

    //初始化gridview
    private void initGrid() {
        grid_list = new ArrayList<>(lists.size());
        for (int a = 0; a < lists.size(); a++) {
            GridView gridView = new GridView(mContext);
            gridView.setNumColumns(5);
            MyAdapter adapter = new MyAdapter(lists.get(a));
            gridView.setAdapter(adapter);
            grid_list.add(gridView);
        }
    }

    public void show() {
        if (quickBeans == null || quickBeans.size() == 0) {
            return;
        }
        initSpot();
        initGrid();
        viewpager.addOnPageChangeListener(new OnPageChangeListener());
        adapter = new ViewPagerAdapter();
        viewpager.setAdapter(adapter);
    }

    private class OnPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            for (int i = 0; i < lists.size(); i++) {
                if (position % grid_list.size() == i) {
                    view_list.get(i).setBackgroundDrawable(gradientDrawableSelected);
                } else {
                    view_list.get(i).setBackgroundDrawable(gradientDrawable);
                }
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    }


    private class ViewPagerAdapter extends PagerAdapter {
        @Override
        public void destroyItem(View container, int position, Object object) {
        }

        @Override
        public Object instantiateItem(View container, int position) {
            position %= grid_list.size();
            if (position < 0) {
                position = grid_list.size() + position;
            }
            GridView gridView = grid_list.get(position);
            ViewParent vp = gridView.getParent();
            if (vp != null) {
                ViewGroup parent = (ViewGroup) vp;
                parent.removeView(gridView);
            }
            ((ViewPager) container).addView(gridView);
            return gridView;
        }

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void restoreState(Parcelable arg0, ClassLoader arg1) {

        }

        @Override
        public Parcelable saveState() {
            return null;
        }

        @Override
        public void startUpdate(View arg0) {
        }

        @Override
        public void finishUpdate(View arg0) {
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public class MyAdapter extends BaseAdapter {

        private LayoutInflater mInflater;
        private List<QuickBean> mlist;

        public MyAdapter(List<QuickBean> list) {
            this.mlist = list;
            mInflater = LayoutInflater.from(mContext);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public QuickBean getItem(int position) {
            return mlist.get(position);
        }

        @Override
        public int getCount() {
            return mlist.size();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.view_item_quick,
                        null);
                viewHolder = new ViewHolder();
                viewHolder.img = (ImageView) convertView.findViewById(R.id.img);
                viewHolder.name = (TextView) convertView
                        .findViewById(R.id.name);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            final QuickBean app = mlist.get(position);
            viewHolder.name.setText(app.getName());
            if (!app.getImg().isEmpty()) {
                Picasso.with(mContext)
                        .load(app.getImg())
                        .into(viewHolder.img);
            }
            convertView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    ToastShow.show(mContext, app.getDesc());
                }
            });
            return convertView;
        }

        class ViewHolder {
            private ImageView img;
            private TextView name;
        }
    }


    public static List<List<QuickBean>> splitList(List<QuickBean> list, int len) {
        if (list == null || list.size() == 0 || len < 1) {
            return null;
        }
        List<List<QuickBean>> result = new ArrayList<List<QuickBean>>();
        int size = list.size();
        int count = (size + len - 1) / len;
        for (int i = 0; i < count; i++) {
            List<QuickBean> subList = list.subList(i * len, ((i + 1) * len > size ? size : len * (i + 1)));
            result.add(subList);
        }
        return result;
    }
}
