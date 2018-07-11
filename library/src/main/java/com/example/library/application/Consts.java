package com.example.library.application;

import android.graphics.Color;

/**
 * Created by Mr'Tang on 2018/4/16.
 * 存放客户端各种开关 以及全局参数
 */

public class Consts {
    public static final boolean IS_ONLINE = true;
    public static final boolean IS_DEBUG = true;

    public static final String MENU_BUILDER = "MenuBuilder";

    public static final String SERVER_IP = IS_ONLINE ? "http://www.wanandroid.com/" : "";

    //登录
    public static final String SERVER_IP_login = SERVER_IP + "/user/login";

    //注册
    public static final String SERVER_IP_register = SERVER_IP + "/user/register";

    //首页banner
    public static final String SERVER_IP_banner = SERVER_IP + "/banner/json";
    public static final String SERVER_IP_banner2 = "https://www.apiopen.top/meituApi?page=1";

    //首页文章列表
    public static final String SERVER_IP_doc_head = SERVER_IP + "/article/list/";
    public static final String SERVER_IP_doc_end = "/json";

    //导航数据
    public static final String SERVER_IP_navigation = SERVER_IP + "/navi/json";

    //体系数据
    public static final String SERVER_IP_system = SERVER_IP + "/tree/json";
    //体系文章数据
    public static final String SERVER_IP_systemdoc_head = SERVER_IP + "/article/list/";
    public static final String SERVER_IP_systemdoc_end = "/json?cid=";
    //项目
    public static final String SERVER_IP_project = "http://www.wanandroid.com/project/tree/json";


    /**
     * Tab colors
     */
    public static final int[] TAB_COLORS = new int[]{
            Color.parseColor("#90C5F0"),
            Color.parseColor("#91CED5"),
            Color.parseColor("#F88F55"),
            Color.parseColor("#C0AFD0"),
            Color.parseColor("#E78F8F"),
            Color.parseColor("#67CCB7"),
            Color.parseColor("#F6BC7E")
    };

    public static final String SHARED_NAME = "_preferences";
    public static final String USERNAME_KEY = "username";
    public static final String PASSWORD_KEY = "password";
    public static final String LOGIN_KEY = "login";

}
