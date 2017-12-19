package com.madongqiang.gifdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class GifInvestigateActivity extends AppCompatActivity {

    private static final String GIF_URL = "http://b-ssl.duitang.com/uploads/item/201502/01/20150201172215_UvVaf.gif";

    private ImageView ivGif;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gif_investigate);

        ivGif = findViewById(R.id.iv_gif);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Glide.with(this)
                .load(GIF_URL)
                .asGif()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .placeholder(R.drawable.default_pic)
                .error(R.drawable.default_pic)
                .into(ivGif);
    }
}
