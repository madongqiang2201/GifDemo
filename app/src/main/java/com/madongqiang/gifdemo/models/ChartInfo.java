package com.madongqiang.gifdemo.models;

import org.json.JSONObject;

/**
 * package：com.madongqiang.gifdemo.models
 * Date：2017/12/19 10:21
 * Version：3.1.0
 * 功能简介：
 * 修改历史：
 *
 * @author 马东强
 */
public class ChartInfo {
    /**
     * 收益，例如：8.00% 最多两位小数
     */
    private float apr;
    /**
     * 该收益对应的项目期限，例如：7 天，1 个月等
     */
    private String timeLimit;

    public ChartInfo(float apr, String timeLimit) {
        this.apr = apr;
        this.timeLimit = timeLimit;
    }

    public ChartInfo(JSONObject response) {
        if (response != null) {
            this.apr = (float) response.optDouble("apr");
            this.timeLimit = response.optString("time_limit");
        }
    }

    public float getApr() {
        return apr;
    }

    public String getTimeLimit() {
        return timeLimit;
    }
}
