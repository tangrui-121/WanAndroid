package com.example.mrtang.wanandroid.moudle.login;

import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.SPUtils;
import com.example.library.application.Consts;
import com.example.library.base.BasicCompatActivity;
import com.example.library.bean.LoginData;
import com.example.library.utils.LogUtils;
import com.example.library.view.RegisterPopupWindow;
import com.example.mrtang.wanandroid.AppMainActivity;
import com.example.mrtang.wanandroid.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Mr'Tang on 2018/5/22.
 */

public class LoginActivity extends BasicCompatActivity<ILoginPresenter> implements ILoginView, View.OnClickListener {
    private ILoginPresenter mPresenter;
    @BindView(R.id.mLoginGroup)
    RelativeLayout mLoginGroup;
    @BindView(R.id.edit_account)
    EditText edit_account;
    @BindView(R.id.edit_password)
    EditText edit_password;
    @BindView(R.id.sign_in)
    Button sign_in;
    @BindView(R.id.login_register)
    TextView login_register;

    private RegisterPopupWindow mPopupWindow;
    private Animation shake;

    private CompositeDisposable disposablelist = new CompositeDisposable();

    @Override
    protected void initPresenter() {
        mPresenter = new LoginPresenterlmpl(this);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
    }

    @Override
    protected void updateViews(boolean isRefresh) {
        shake = AnimationUtils.loadAnimation(this, R.anim.shake);
        sign_in.setOnClickListener(this);
        login_register.setOnClickListener(this);
        mPopupWindow = new RegisterPopupWindow(this, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
        mPopupWindow.setAnimationStyle(R.style.popup_window_animation);
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setBackgroundAlpha();
                login_register.setOnClickListener(LoginActivity.this);
            }
        });

        maopao();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in:
                String account = edit_account.getText().toString().trim();
                String password = edit_password.getText().toString().trim();
                if (account.isEmpty()) {
                    edit_account.setAnimation(shake);
                    return;
                }
                if (password.isEmpty()) {
                    edit_password.setAnimation(shake);
                    return;
                }
                mPresenter.login(this, account, password);
                break;
            case R.id.login_register:
                mPopupWindow.showAtLocation(mLoginGroup, Gravity.CENTER, 0, 0);
                login_register.setOnClickListener(null);
                break;
            default:
                break;
        }
    }

    private void register() {
        if (mPopupWindow == null || mPresenter == null) {
            return;
        }
        String account = mPopupWindow.mUserNameEdit.getText().toString().trim();
        String password = mPopupWindow.mPasswordEdit.getText().toString().trim();
        String rePassword = mPopupWindow.mRePasswordEdit.getText().toString().trim();
        if (account.isEmpty()) {
            mPopupWindow.mUserNameEdit.setAnimation(shake);
            return;
        }
        if (password.isEmpty()) {
            mPopupWindow.mPasswordEdit.setAnimation(shake);
            return;
        }
        if (rePassword.isEmpty()) {
            mPopupWindow.mRePasswordEdit.setAnimation(shake);
            return;
        }
        if (!password.equals(rePassword)) {
            showError(getString(R.string.password_not_same));
            return;
        }
        mPresenter.register(this, account, password);
    }

    @Override
    public void doLoginEvent(LoginData loginData) {
        SPUtils.getInstance(Consts.SHARED_NAME).put(Consts.LOGIN_KEY, true);
        SPUtils.getInstance(Consts.SHARED_NAME).put(Consts.USERNAME_KEY, loginData.getUsername());
        SPUtils.getInstance(Consts.SHARED_NAME).put(Consts.PASSWORD_KEY, loginData.getPassword());

        Intent intent = new Intent(this, AppMainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void showError(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 设置屏幕透明度
     */
    public void setBackgroundAlpha() {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        // 0.0~1.0
        lp.alpha = 1.0f;
        getWindow().setAttributes(lp);
    }


    //冒泡排序
    //从后向前两两比较  最小的往前排  一直循环下去
    private void maopao() {
        int[] arr = {10, 9, 444, 6666, 34, 93, 567, 456};
        int temp;
        boolean flag;
        for (int i = 0; i < arr.length - 1; i++) {
            LogUtils.e("I ==" + i);
            flag = false;
            for (int j = arr.length - 1; j > i; j--) {
                if (arr[j] < arr[j - 1]) {
                    flag = true;
                    temp = arr[j - 1];
                    arr[j - 1] = arr[j];
                    arr[j] = temp;
                }
            }
            if (!flag)
                break;
        }
        for (int c = 0; c < arr.length; c++) {
            LogUtils.e(arr[c] + "");
        }
    }

    //选择排序
    //遍历n-1次最小的和第一个交换   循环最小的和第二个交换  。。。
    private void xuanze() {
        int[] arr = {10, 9, 444, 6666, 34, 93, 567, 456};

        for (int i = 0; i < arr.length - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }
            if (minIndex != i) {
                int temp = arr[i];
                arr[i] = arr[minIndex];
                arr[minIndex] = temp;
            }
        }
    }

    //插入排序
    //前两个比较 前三个比较  循环下去
    public static void insert_sort() {
        int[] arr = {10, 9, 444, 6666, 34, 93, 567, 456};
        int temp;
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = i + 1; j > 0; j--) {
                if (arr[j] < arr[j - 1]) {
                    temp = arr[j - 1];
                    arr[j - 1] = arr[j];
                    arr[j] = temp;
                } else {
                    break;
                }
            }
        }
    }


    private void rxjava() {
        //上游 发射者
        Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
                e.onComplete();
            }
        });

        //下游 接受者
        Observer<Integer> observer = new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Integer integer) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };

        //建立连接
        observable.subscribeOn(Schedulers.newThread())//指定上游线程  只有第一次指定有效
                .observeOn(AndroidSchedulers.mainThread())//指定下游线程  多次指定有效
                .subscribe(observer);

        //链式操作
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
                e.onComplete();
            }
        }).map(new Function<Integer, String>() {//int ==> string  map转换符
            @Override
            public String apply(Integer integer) throws Exception {
                return null;
            }
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    Disposable disposable;
                    int i;

                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                        disposablelist.add(d);
                    }

                    @Override
                    public void onNext(String s) {

                        i++;
                        if (i == 2) {
                            //将2关闭 则2之后的不再接受，发送正常
                            disposable.dispose();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }


    @Override
    protected void onDestroy() {
        //防止在子线程操作的时候activity退出了，这时将disposable全清除掉，这样上游随他发，下游主线程并不会去接受
        disposablelist.clear();
        super.onDestroy();
    }
}