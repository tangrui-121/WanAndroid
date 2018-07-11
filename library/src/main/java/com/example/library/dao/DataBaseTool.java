package com.example.library.dao;


import com.example.library.application.App;
import com.example.library.bean.LoginData;

/**
 * Created by Mr'Tang on 2018/5/28.
 */

public class DataBaseTool {
    private static LoginDataDao mLoginDataDao = App.getInstances().getDaoSession().getLoginDataDao();

    /**
     * 增加用户信息
     */
    public static void insertUserInfo(LoginData info) {
        mLoginDataDao.insert(info);
    }

    /**
     * 查用户信息
     */
    public static LoginData SearchUserInfo() {
        //惰性加载
        LoginData list = mLoginDataDao.queryBuilder().unique();
        return list;
    }

    /**
     * 删除某条用户信息
     *
     * @param i 删除数据的id
     */
    public static void deleteUserInfo(long i) {
        mLoginDataDao.deleteByKey(i);
    }

    /**
     * 修改某条用户信息
     */
    public static void correctUserInfo(LoginData info) {
        mLoginDataDao.update(info);
    }
}
