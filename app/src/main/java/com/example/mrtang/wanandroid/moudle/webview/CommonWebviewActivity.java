package com.example.mrtang.wanandroid.moudle.webview;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.library.application.Consts;
import com.example.library.base.BasicTitleCompatActivity;
import com.example.library.bean.DoclistBean;
import com.example.library.okhttp.CollectEvent;
import com.example.library.utils.ToastShow;
import com.example.mrtang.wanandroid.R;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.Method;

/**
 * Created by Mr'Tang on 2018/4/24.
 */

public class CommonWebviewActivity extends BasicTitleCompatActivity implements ICommonWebviewView {

    private ICommonWebviewPresenter mPresenter;

    private static final String KEY_SHOW = "show";
    private static final String KEY_POSITON = "position";
    private static final String KEY_DOCBEAN = "docbean";
    private static final String KEY_FROM = "from";
    private WebView mWebView;
    private ProgressBar progress;
    private MenuItem menuItem;
    private DoclistBean doclistBean;
    private int position;

    //from为-1的时候代表从收藏界面进来的  取消收藏就需要不同的接口
    public static void lunchActivity(Context mActivity, ActivityOptions activityOptions, int position, DoclistBean doclistBean, boolean is_show, int from) {
        Intent intent = new Intent(mActivity, CommonWebviewActivity.class);
        intent.putExtra(KEY_POSITON, position);
        intent.putExtra(KEY_DOCBEAN, doclistBean);
        intent.putExtra(KEY_SHOW, is_show);
        intent.putExtra(KEY_FROM, from);
        if (activityOptions != null && !Build.BOARD.contains("samsung") && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mActivity.startActivity(intent, activityOptions.toBundle());
        } else {
            mActivity.startActivity(intent);
        }
    }

    @Override
    protected void initPresenter() {
        mPresenter = new CommonWebviewPresenterlmpl(this);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_commonwebview;
    }

    @Override
    protected void initView() {
        progress = (ProgressBar) findViewById(R.id.progress);
        mWebView = (WebView) findViewById(R.id.webView);
    }

    @Override
    protected void updateViews(boolean isRefresh) {
        if (null == getIntent()) {
            finish();
            return;
        }
        doclistBean = (DoclistBean) getIntent().getSerializableExtra(KEY_DOCBEAN);
        position = getIntent().getIntExtra(KEY_POSITON, 0);
        String url = doclistBean.getLink();

        if (!url.startsWith("http") && !url.startsWith("https")) {
            ToastShow.show(this, "地址错误");
            return;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mWebView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        mWebView.removeJavascriptInterface("searchBoxJavaBridge_");
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setAppCacheEnabled(false);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        //不显示缩放按钮
        webSettings.setDisplayZoomControls(false);
        //设置自适应屏幕，两者合用
        //将图片调整到适合WebView的大小
        webSettings.setUseWideViewPort(true);
        //缩放至屏幕的大小
        webSettings.setLoadWithOverviewMode(true);
        //自适应屏幕
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setLoadWithOverviewMode(true);
        mWebView.setWebChromeClient(webChromeClient);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith("http:") || url.startsWith("https:")) {
                    return false;
                }
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri
                            .parse(url));
                    startActivity(intent);
                } catch (Exception e) {

                }
                return true;
            }
        });
        mWebView.requestFocus();
        mWebView.loadUrl(url);
    }

    WebChromeClient webChromeClient = new WebChromeClient() {
        @Override
        public void onShowCustomView(View view, WebChromeClient.CustomViewCallback callback) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            if (myCallback != null) {
                myCallback.onCustomViewHidden();
                myCallback = null;
                return;
            }
            ViewGroup parent = (ViewGroup) mWebView.getParent();
            parent.removeView(mWebView);
            parent.addView(view);
            myView = view;
            myCallback = callback;
            webChromeClient = this;
        }

        private View myView = null;
        private WebChromeClient.CustomViewCallback myCallback = null;

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress == 100) {
                progress.setVisibility(View.GONE);
            } else {
                progress.setProgress(newProgress);
                progress.setVisibility(View.VISIBLE);
            }
            super.onProgressChanged(view, newProgress);
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            if (title.isEmpty() || "".equals(title)) {
                String mtitle = doclistBean.getTitle();
                setTitle(TextUtils.isEmpty(mtitle) ? "详情" : mtitle);
            } else {
                setTitle(title);
            }
        }

        public void onHideCustomView() {
            if (myView != null) {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                if (myCallback != null) {
                    myCallback.onCustomViewHidden();
                    myCallback = null;
                }
                ViewGroup parent = (ViewGroup) myView.getParent();
                parent.removeView(myView);
                parent.addView(mWebView);
                myView = null;
            }
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        if (mWebView != null) {
            mWebView.reload();
        }
    }

    protected void onDestroy() {
        super.onDestroy();
        if (mWebView != null) {
            mWebView.removeAllViews();
            mWebView.destroy();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            supportFinishAfterTransition();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.right_webview_menu, menu);
        menuItem = menu.findItem(R.id.action_collect);
        if (!getIntent().getBooleanExtra(KEY_SHOW, false)) {
            menuItem.setVisible(false);
        } else {
            if (doclistBean.getCollect())
                menuItem.setIcon(R.mipmap.icon_hadcollect);
        }
        return true;
    }

    public void after_collect(DoclistBean doclistBean) {
        if (doclistBean.getCollect()) {
            menuItem.setIcon(R.mipmap.icon_hadcollect);
        } else {
            menuItem.setIcon(R.mipmap.icon_collect);
        }
        EventBus.getDefault().post(new CollectEvent(position, doclistBean));
    }


    public boolean onOptionsItemSelectedCompat(MenuItem item) {
        int i = item.getItemId();
        switch (i) {
            case R.id.action_collect:
                if (doclistBean.getCollect()) {
                    if (getIntent().getIntExtra(KEY_FROM, 0) == -1) {
                        mPresenter.uncollectdoc(position, doclistBean);
                    } else {
                        mPresenter.uncollectDoc(position, doclistBean);
                    }
                } else {
                    mPresenter.collectDoc(position, doclistBean);
                }
                break;
            case R.id.action_share:
                ToastShow.show(this, "分享");
                break;
            case R.id.action_browser:
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse(doclistBean.getLink());
                intent.setData(content_url);
                startActivity(intent);
                break;
            default:
                break;
        }
        return false;
    }

    /**
     * 让菜单同时显示图标和文字
     */
    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        if (menu != null) {
            if (Consts.MENU_BUILDER.equalsIgnoreCase(menu.getClass().getSimpleName())) {
                try {
                    @SuppressLint("PrivateApi")
                    Method method = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    method.setAccessible(true);
                    method.invoke(menu, true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return super.onMenuOpened(featureId, menu);
    }

    @Override
    public void showError(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
