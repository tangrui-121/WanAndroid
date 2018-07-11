package com.example.mrtang.wanandroid.fragment.homefragment;

import com.example.library.bean.BannerBean;
import com.example.library.bean.Doc;
import com.example.library.bean.DoclistBean;
import com.example.library.okhttp.ApiServices;
import com.example.library.okhttp.DataResponse;
import com.example.library.okhttp.RetrofitManager;
import com.example.library.okhttp.RxSchedulers;
import com.example.mrtang.wanandroid.moudle.collectutils.CollectUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;

/**
 * Created by Mr'Tang on 2018/4/24.
 */

public class HomeFragmentlmpl implements IHomePresenter {
    //写这里的时候想了半天  因为banner是add的，doc和banner数据处理不好，，，，，rxjava的zip可以解决
    private IHomeView iView;

    public HomeFragmentlmpl(IHomeView iView) {
        this.iView = iView;
    }

    public void collectDoc(final int position, final DoclistBean bean) {
        CollectUtils.collectDoc(iView, position, bean);
    }

    public void uncollectDoc(final int position, final DoclistBean bean) {
        CollectUtils.uncollectDoc(iView, position, bean);
    }

    //使用zip运算符来实现
    public void rxjava_zip(final int mpage) {
        Observable<DataResponse<List<BannerBean>>> observable1 = RetrofitManager.create(ApiServices.class).getBanner();
        Observable<DataResponse<Doc>> observable2 = RetrofitManager.create(ApiServices.class).getHomeArticles(mpage);
        Observable.zip(observable1, observable2, new BiFunction<DataResponse<List<BannerBean>>, DataResponse<Doc>, Map<String, Object>>() {
            @Override
            public Map<String, Object> apply(DataResponse<List<BannerBean>> listDataResponse, DataResponse<Doc> docDataResponse) throws Exception {
                Map<String, Object> objMap = new HashMap<>();
                objMap.put("banner", listDataResponse.getData());
                objMap.put("doc", docDataResponse.getData());
                return objMap;
            }
        }).compose(RxSchedulers.<Map<String, Object>>applySchedulers()).compose(iView.<Map<String, Object>>bindToLife()).subscribe(new Consumer<Map<String, Object>>() {
            @Override
            public void accept(Map<String, Object> map) throws Exception {
                List<BannerBean> banners = (List<BannerBean>) map.get("banner");
                Doc doc = (Doc) map.get("doc");
                iView.initBanner(banners);
                iView.showError(mpage == 0 ? "最新内容加载完毕~" : "第" + (mpage + 1) + "页~");
                iView.initData(doc.getList(), mpage == 0);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                iView.showError(throwable.getMessage());
            }
        });
    }
}
