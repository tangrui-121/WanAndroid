package com.example.mrtang.wanandroid.fragment.projectfragment;

import android.content.Context;

import com.alibaba.fastjson.JSONObject;
import com.example.library.application.Consts;
import com.example.library.bean.NavigationChildBean;
import com.example.library.http.CallBackListener;
import com.example.library.http.CallServer;
import com.example.library.http.RequestUtils;
import com.example.library.http.Result;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;

import java.util.List;

/**
 * Created by Mr'Tang on 2018/5/17.
 */

public class ProjectFragmentlmpl implements IProjectPresenter, CallBackListener {
    private IProjectView iView;
    private Request request;

    public ProjectFragmentlmpl(IProjectView view) {
        this.iView = view;
    }


    public void getProjectData(Context context) {
        request = RequestUtils.getInfo(Consts.SERVER_IP_project);
        if (context == null) {
            CallServer.getInstance().add(1, request, this);
        } else {
            CallServer.getInstance().add(context, 1, request, this);
        }
    }

    @Override
    public void onUpdate(int what, Response<JSONObject> data, boolean success) {
        switch (what) {
            case 1:
                if (success) {
                    if (data.get().getIntValue("errorCode") == Result.RESULT_OK) {
                        List<NavigationChildBean> items = NavigationChildBean.parseFromJson1(data.get().getJSONArray("data"));
                        if (items != null && items.size() != 0) {
                            iView.initData(items);
                        } else {
                            iView.showError("暂时木有项目数据~~");
                        }
                    } else {
                        iView.showError(data.get().getString("errorMsg"));
                    }
                }
                break;
            default:
                break;
        }
    }
}
