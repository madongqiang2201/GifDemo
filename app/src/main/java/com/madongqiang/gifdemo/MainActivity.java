package com.madongqiang.gifdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnGifInvestigate;
    private Button btnFingerRecognition;
    private TextView txtNum;
    private Button btnAdd;
    private Button btnBarChart;

    private double currentNum = 10000.1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnGifInvestigate = findViewById(R.id.btn_gif_investigate);
        btnFingerRecognition = findViewById(R.id.btn_finger_recognition);
        txtNum = findViewById(R.id.txt_num);
        btnAdd = findViewById(R.id.btn_add);
        btnBarChart = findViewById(R.id.btn_bar_chart);

        btnGifInvestigate.setOnClickListener(this);
        btnFingerRecognition.setOnClickListener(this);
        btnAdd.setOnClickListener(this);
        btnBarChart.setOnClickListener(this);

        txtNum.setBackgroundColor(0x00ff00);
        txtNum.setText(currentNum + "");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_gif_investigate:
                startActivity(new Intent(this, GifInvestigateActivity.class));
                break;
            case R.id.btn_finger_recognition:
                startActivity(new Intent(this, FingerRecognitionActivity.class));
                break;
            case R.id.btn_add:
                currentNum += 0.1;
                txtNum.setText("" + currentNum);
                break;
            case R.id.btn_bar_chart:
                startActivity(new Intent(this, BarChartActivity.class));
                break;
            default:
                break;
        }
    }
}
