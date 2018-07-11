package com.example.mrtang.wanandroid.fragment.knowledgefragment.knowlist;

import android.content.Context;
import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.example.library.application.Consts;
import com.example.library.bean.DoclistBean;
import com.example.library.http.CallBackListener;
import com.example.library.http.CallServer;
import com.example.library.http.RequestUtils;
import com.example.library.http.Result;
import com.example.mrtang.wanandroid.moudle.collectutils.CollectUtils;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;

import java.util.List;

/**
 * Created by Mr'Tang on 2018/5/16.
 */

public class KnowlistFragmentlmpl implements CallBackListener, IKnowlistPresenter {
    private IKnowlistView iView;
    private Request request;
    private int page = 0;

    public KnowlistFragmentlmpl(IKnowlistView view) {
        this.iView = view;
    }

    public void getKnowlistData(Context context, int id, int mpage) {
        page = mpage;
        request = RequestUtils.getInfo(Consts.SERVER_IP_systemdoc_head + page + Consts.SERVER_IP_systemdoc_end + id);
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
                    JSONObject jsonObject = data.get().getJSONObject("data");
                    if (jsonObject.getIntValue("errorCode") == Result.RESULT_OK) {
                        List<DoclistBean> items = DoclistBean.parseFromJson(jsonObject.getJSONArray("datas"));
                        if (items.size() > 0) {
                            iView.initData(items, page == 0);
                        } else {
                            iView.showError("文章空空如也~");
                        }
                    } else {
                        iView.showError(jsonObject.getString("errorMsg"));
                    }
                }
                break;
            default:
                break;
        }

    }

    public void collectDoc(final int position, final DoclistBean bean) {
        CollectUtils.collectDoc(iView, position, bean);
    }

    public void uncollectDoc(final int position, final DoclistBean bean) {
        CollectUtils.uncollectDoc(iView, position, bean);
    }
}