package com.madongqiang.gifdemo.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.madongqiang.gifdemo.R;

/**
 * package：com.madongqiang.gifdemo.views
 * Date：2017/12/13 16:37
 * Version：3.1.0
 * 功能简介：
 * 修改历史：
 *
 * @author 马东强
 */
public class CoinBarChartView extends View {
    private Paint mPaint;
    private RectF rectF;
    private Bitmap bitmap;

    public CoinBarChartView(Context context) {
        super(context);
        init();
    }

    public CoinBarChartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CoinBarChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
//        mPaint.setColor(0x33ffff00);
        mPaint.setStyle(Paint.Style.FILL);

        Shader mShader = new LinearGradient(0, 0, 100, 100,
                new int[] {0x99ffff00, 0xeeffff00}, null, Shader.TileMode.REPEAT); // 一个材质,打造出一个线性梯度沿著一条线。
        mPaint.setShader(mShader);

        rectF = new RectF();
        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        canvas.drawOval();
        for (int i = 0; i < 5; i++) {
            rectF.set(200,1000 - 80 * i,500,1150 - 80 * i);
            canvas.drawOval(rectF, mPaint);
        }
        rectF.set(200, 1000 - 320, 500, 830);
        canvas.drawBitmap(bitmap, null, rectF, mPaint);
    }
}
