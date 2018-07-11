package com.example.mrtang.wanandroid.moudle.set;

import android.content.Context;
import android.content.Intent;

import com.example.library.base.BasicTitleCompatActivity;
import com.example.library.bean.CS;
import com.example.library.bean.QuickBean;
import com.example.library.view.MyQuick;
import com.example.library.view.MySpannView;
import com.example.mrtang.wanandroid.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mr'Tang on 2018/6/27.
 */

public class SetActivity extends BasicTitleCompatActivity {

    private MySpannView cs;
    private MyQuick quick;

    public static void launch(Context context) {
        Intent intent = new Intent(context, SetActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void showError(String msg) {

    }

    @Override
    protected void initPresenter() {

    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_set;
    }

    @Override
    protected void initView() {
        cs = (MySpannView) findViewById(R.id.cs);
        final List<CS> list = new ArrayList<>();
        for (int a = 0; a < 5; a++) {
            CS cs = new CS();
            cs.setId(a);
            cs.setCsname("cs" + a);
            list.add(cs);
        }
        cs.setListDatas(list);

        quick = (MyQuick) findViewById(R.id.quick);
        final List<QuickBean> quickBeans = new ArrayList<>();
        for (int a = 0; a < 35; a++) {
            QuickBean quickBean = new QuickBean();
            quickBean.setName("应用" + a);
            quickBean.setDesc("描述" + a);
            quickBean.setImg("");
            quickBeans.add(quickBean);
        }
        quick.setData(quickBeans);
        quick.show();
    }

    @Override
    protected void updateViews(boolean isRefresh) {
        setTitle("设置");
    }
}
