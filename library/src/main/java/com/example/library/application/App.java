package com.example.library.application;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.blankj.utilcode.util.Utils;
import com.example.library.dao.DaoMaster;
import com.example.library.dao.DaoSession;
import com.yanzhenjie.nohttp.NoHttp;

public class App extends Application {
    public static App _mContext;

    private DaoMaster.DevOpenHelper mHelper;
    private SQLiteDatabase db;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;

    public static App getInstances() {
        return _mContext;
    }



    @Override
    public void onCreate() {
        super.onCreate();
        _mContext = this;
        NoHttp.initialize(this);
        Utils.init(this);
        setDatabase();
    }

    /**
     * 设置greenDao
     */
    private void setDatabase() {
        mHelper = new DaoMaster.DevOpenHelper(this, "login-db", null);
        db = mHelper.getWritableDatabase();
        mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }

    public static Context getAppContext() {
        return _mContext.getApplicationContext();
    }
}
