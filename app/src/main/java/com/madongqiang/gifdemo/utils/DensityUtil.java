package com.madongqiang.gifdemo.utils;

import android.content.Context;

import java.text.DecimalFormat;

/**
 * package：com.madongqiang.gifdemo.utils
 * Date：2017/12/19 10:45
 * Version：3.1.0
 * 功能简介：
 * 修改历史：
 *
 * @author 马东强
 */
public class DensityUtil {

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static String addZero(double value) {
        DecimalFormat decimalFormat = new DecimalFormat("#0.00");
        return decimalFormat.format(value);
    }
}
