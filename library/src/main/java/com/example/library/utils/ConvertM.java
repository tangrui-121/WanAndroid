package com.example.library.utils;

/**
 * Created by Mr'Tang on 2018/4/17.
 */

import android.content.Context;
import android.util.TypedValue;

/**
 * 转换工具类
 */
public class ConvertM {
    /**
     * @param context
     * @param dpVal
     * @return
     */
    public static int dp2px(Context context, float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, context.getResources().getDisplayMetrics());
    }
}