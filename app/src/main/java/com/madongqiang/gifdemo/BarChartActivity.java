package com.madongqiang.gifdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.madongqiang.gifdemo.models.ChartInfo;
import com.madongqiang.gifdemo.views.EarningBarChartView;

import java.util.ArrayList;
import java.util.List;

public class BarChartActivity extends AppCompatActivity {

    private EarningBarChartView chartView;
    private List<ChartInfo> datas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_chart);
        chartView = findViewById(R.id.bar_chart_view);

        for (int i = 0; i < 8; i++) {
            datas.add(new ChartInfo(5.55f + i, (i + 1) + "个月"));
        }
        datas.add(new ChartInfo(10, 8 + "个月"));
        datas.add(new ChartInfo(5, 8 + "个月"));
        chartView.setDatas(datas);

        chartView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chartView.setPlayAprAnimation(true);
            }
        });
    }
}
