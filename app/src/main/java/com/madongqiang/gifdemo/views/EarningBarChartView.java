package com.madongqiang.gifdemo.views;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import com.madongqiang.gifdemo.R;
import com.madongqiang.gifdemo.models.ChartInfo;
import com.madongqiang.gifdemo.utils.DensityUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * package：com.madongqiang.gifdemo.views
 * Date：2017/12/18 15:42
 * Version：3.1.0
 * 功能简介：
 * 修改历史：
 *
 * @author 马东强
 */
public class EarningBarChartView extends View {
    /**
     * 柱状图画笔
     */
    private Paint mBarPaint;
    /**
     * 虚线画笔
     */
    private Paint mLinePaint;
    /**
     * 期限画笔
     */
    private Paint mTextPaint;
    /**
     * 锁定期画笔
     */
    private Paint mLockTextPaint;
    /**
     * 锁定期画笔
     */
    private Paint mLockPeriodPaint;
    /**
     * 非锁定期画笔
     */
    private Paint mUnlockPeriodPaint;
    /**
     * 利率画笔
     */
    private Paint mAprPaint;
    /**
     * 虚线 Path
     */
    private Path linePath;
    /**
     * 顶部红色图、中间红色/黄色图、底部白色图
     */
    private Bitmap mBitmapTop;
    private Bitmap mBitmapCenterRed;
    private Bitmap mBitmapCenterYellow;
    private Bitmap mBitmapBottom;
    /**
     * 图标轮廓 Rect
     */
    private Rect rectSrc;
    /**
     * 图标绘制范围 Rect
     */
    private RectF rectFDest;
    /**
     * 获取文字长宽的 Rect
     */
    private Rect rectTextBounds;
    /**
     * 要绘制的数据
     */
    private List<ChartInfo> datas = new ArrayList<>();
    /**
     * 柱状图左侧边距，累加的数据
     */
    private int dividerWidth = 0;
    /**
     * 普通金币的宽高（因为底部白色图标比上面红色黄色的大，所以需独立计算）
     */
    private int coinWidth;
    private int coinHeight;
    /**
     * 底部白图标的宽高（因为底部白色图标比上面红色黄色的大，所以需独立计算）
     */
    private int bottomCoinWidth;
    private int bottomCoinHeight;
    /**
     * 同一个柱状图，两个图标之间，每次上移的偏移量
     */
    private int translateX;
    /**
     * 两个柱状图之间的空白间隔区域宽度
     */
    private int dividerSpace;
    /**
     * 柱状图底部控件高度
     */
    private int defaultBottomViewHeight;
    /**
     * 锁定期/随时可退 文案
     */
    private String txtLockPeriod;
    private String txtUnlockPeriod;
    /**
     * 最大利率，用来计算控件高度
     */
    private float maxApr;

    private boolean playAnimation;

    public EarningBarChartView(Context context) {
        super(context);
        init();
    }

    public EarningBarChartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public EarningBarChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mBarPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBarPaint.setFilterBitmap(true);
        mBarPaint.setDither(true);

        mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLinePaint.setAntiAlias(true);
        mLinePaint.setStyle(Paint.Style.STROKE);
        mLinePaint.setStrokeWidth(1);
        mLinePaint.setColor(getResources().getColor(R.color.color_line));
        DashPathEffect effects = new DashPathEffect(new float[]{5,5,5,5},1);
        mLinePaint.setPathEffect(effects);
        mLinePaint.setStrokeJoin(Paint.Join.ROUND);

        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(getResources().getColor(R.color.color_text));
        mTextPaint.setTextSize(getResources().getDimensionPixelSize(R.dimen.chart_time_limit_text_size));

        mLockTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLockTextPaint.setColor(getResources().getColor(R.color.color_text));
        mLockTextPaint.setTextSize(getResources().getDimensionPixelSize(R.dimen.chart_lock_text_size));

        mLockPeriodPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLockPeriodPaint.setColor(getResources().getColor(R.color.color_lock_period));
        mLockPeriodPaint.setStyle(Paint.Style.FILL);

        mUnlockPeriodPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mUnlockPeriodPaint.setColor(getResources().getColor(R.color.color_unlock_period));
        mUnlockPeriodPaint.setStyle(Paint.Style.FILL);

        mAprPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mAprPaint.setColor(getResources().getColor(R.color.color_apr));
        mAprPaint.setTextSize(getResources().getDimensionPixelSize(R.dimen.chart_lock_text_size));

        linePath = new Path();

        Resources resources = getResources();
        mBitmapTop = BitmapFactory.decodeResource(resources, R.mipmap.earning_top);
        mBitmapCenterRed = BitmapFactory.decodeResource(resources, R.mipmap.earning_center_red);
        mBitmapCenterYellow = BitmapFactory.decodeResource(resources, R.mipmap.earning_center_yellow);
        mBitmapBottom = BitmapFactory.decodeResource(resources, R.mipmap.earning_bottom);

        Drawable temp = resources.getDrawable(R.mipmap.earning_top);
        Drawable bottom = resources.getDrawable(R.mipmap.earning_bottom);
        bottomCoinWidth = bottom.getMinimumWidth();
        bottomCoinHeight = bottom.getMinimumHeight();
        coinWidth = temp.getMinimumWidth();
        coinHeight = temp.getMinimumHeight();

        dividerSpace = (int) resources.getDimension(R.dimen.divider_width);
        translateX = (int) resources.getDimension(R.dimen.translate_x);
        defaultBottomViewHeight = (int) resources.getDimension(R.dimen.default_bottom_view_height);

        rectSrc = new Rect();
        rectFDest = new RectF();
        rectTextBounds = new Rect();

        txtLockPeriod = resources.getString(R.string.lock_period);
        txtUnlockPeriod = resources.getString(R.string.unlock_period);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int desiredWidth = 0;
        String text = datas.get(0).getApr() + "%";
        mAprPaint.getTextBounds(text, 0, text.length(), rectTextBounds);
        int desiredHeight = (int) (defaultBottomViewHeight + maxApr * translateX + coinHeight + rectTextBounds.height() + DensityUtil.dip2px(getContext(), 15));

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int height = 0;

        if (datas != null && datas.size() != 0) {
            if (heightMode == MeasureSpec.EXACTLY) {
                height = heightSize;
            } else if (heightMode == MeasureSpec.AT_MOST) {
                height = Math.min(desiredHeight, heightSize);
            } else {
                height = desiredHeight;
            }

            // 为了可以左右滑动，宽度根据实际情况计算
            desiredWidth = dividerSpace * (datas.size() + 1) + bottomCoinWidth * datas.size();
        }

        setMeasuredDimension(desiredWidth, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (datas == null || datas.size() == 0) {
            return;
        }

        canvas.drawColor(Color.WHITE);
        drawLineChart(canvas);
        drawBarChart(canvas);
        drawTimeLimit(canvas);
        drawLockupPeriod(canvas);
        drawApr(canvas);
    }

    private void drawApr(Canvas canvas) {
        for (int i = 0; i < datas.size(); i++) {
            float currentData = datas.get(i).getApr();
            String formatApr = DensityUtil.addZero(currentData) + "%";
            mAprPaint.getTextBounds(formatApr, 0, formatApr.length(), rectTextBounds);
            if (i == 0) {
                dividerWidth += dividerSpace;
            } else {
                dividerWidth += (dividerSpace + bottomCoinWidth);
            }
            // x 是两个柱体之间的空白间距 + 柱体宽度的一半 - 文字宽度的一半
            // y 是柱状图底部 - 柱状图高度 - 偏移量(和柱体的间距)
            canvas.drawText(formatApr, dividerWidth + bottomCoinWidth / 2 - rectTextBounds.width() / 2, getMeasuredHeight() - defaultBottomViewHeight - currentData * translateX - DensityUtil.dip2px(getContext(), 8) - rectTextBounds.height(), mAprPaint);
            rectTextBounds.setEmpty();
        }
        dividerWidth = 0;
    }

    private void drawLockupPeriod(Canvas canvas) {
        int lockPeriodWidth = bottomCoinWidth + dividerSpace;
        int yAlias = getMeasuredHeight() - defaultBottomViewHeight / 2 + rectTextBounds.height();
        canvas.drawRect(dividerSpace / 2, yAlias, dividerSpace / 2 + lockPeriodWidth, yAlias + DensityUtil.dip2px(getContext(), 10), mLockPeriodPaint);

        canvas.drawRect(dividerSpace / 2, yAlias - DensityUtil.dip2px(getContext(), 10), dividerSpace / 2 + DensityUtil.dip2px(getContext(), 1), yAlias, mLockPeriodPaint);
        canvas.drawRect(dividerSpace / 2 + lockPeriodWidth - DensityUtil.dip2px(getContext(), 1), yAlias - DensityUtil.dip2px(getContext(), 10), dividerSpace / 2 + lockPeriodWidth, yAlias, mLockPeriodPaint);

        canvas.drawRect(dividerSpace / 2 + lockPeriodWidth, yAlias, getMeasuredWidth() - dividerSpace / 2, yAlias +  DensityUtil.dip2px(getContext(), 10), mUnlockPeriodPaint);

        mLockTextPaint.getTextBounds(txtLockPeriod, 0, txtLockPeriod.length(), rectTextBounds);
        canvas.drawText(txtLockPeriod, dividerSpace / 2 + lockPeriodWidth / 2 - rectTextBounds.width() / 2, yAlias + rectTextBounds.height() +  DensityUtil.dip2px(getContext(), 18), mLockTextPaint);
        rectTextBounds.setEmpty();

        mLockTextPaint.getTextBounds(txtUnlockPeriod, 0, txtUnlockPeriod.length(), rectTextBounds);
        canvas.drawText(txtUnlockPeriod, (getResources().getDisplayMetrics().widthPixels - (dividerSpace / 2 + lockPeriodWidth)) / 2 + (dividerSpace / 2 + lockPeriodWidth) - rectTextBounds.width() / 2, yAlias + rectTextBounds.height() +  DensityUtil.dip2px(getContext(), 18), mLockTextPaint);
        rectTextBounds.setEmpty();
    }

    private void drawTimeLimit(Canvas canvas) {
        for (int i = 0; i < datas.size(); i++) {
            String timeLimit = datas.get(i).getTimeLimit();

            if (i == 0) {
                dividerWidth += dividerSpace;
            } else {
                dividerWidth += (dividerSpace + bottomCoinWidth);
            }
            if (!TextUtils.isEmpty(timeLimit)) {
                mTextPaint.getTextBounds(timeLimit, 0, timeLimit.length(), rectTextBounds);
                // x 是两个柱体之间的空白间距 + 柱体宽度的一半 - 文字宽度的一半
                // y 是柱状图底部 + 文字高度 + 偏移量(和柱体及虚线的间距)
                canvas.drawText(timeLimit, dividerWidth + bottomCoinWidth / 2 - rectTextBounds.width() / 2, getMeasuredHeight() - defaultBottomViewHeight + rectTextBounds.height() + DensityUtil.dip2px(getContext(), 3), mTextPaint);
                rectTextBounds.setEmpty();
            }
        }

        dividerWidth = 0;
    }

    private void drawLineChart(Canvas canvas) {
        linePath.moveTo(dividerSpace / 2, getMeasuredHeight() - defaultBottomViewHeight + 1);
        linePath.lineTo(dividerSpace * (datas.size() + 1) + bottomCoinWidth * datas.size() - dividerSpace / 2, getMeasuredHeight() - defaultBottomViewHeight + 1);

        linePath.moveTo(dividerSpace / 2, getMeasuredHeight() - defaultBottomViewHeight - 1 - 5 * translateX - coinHeight / 2);
        linePath.lineTo(dividerSpace * (datas.size() + 1) + bottomCoinWidth * datas.size() - dividerSpace / 2, getMeasuredHeight() - defaultBottomViewHeight - 1 - 5 * translateX  - coinHeight / 2);

        linePath.moveTo(dividerSpace / 2, getMeasuredHeight() - defaultBottomViewHeight - 1 - 10 * translateX - coinHeight / 2);
        linePath.lineTo(dividerSpace * (datas.size() + 1) + bottomCoinWidth * datas.size() - dividerSpace / 2, getMeasuredHeight() - defaultBottomViewHeight - 1 - 10 * translateX  - coinHeight / 2);

        canvas.drawPath(linePath, mLinePaint);
    }

    private void drawBarChart(Canvas canvas) {
        for (int i = 0; i < datas.size(); i++) {
            float currentData = datas.get(i).getApr();
            if (i == 0) {
                dividerWidth += dividerSpace;
                rectFDest.left = dividerWidth;
            } else {
                dividerWidth += (dividerSpace + bottomCoinWidth);
                rectFDest.left = dividerWidth;
            }

            rectFDest.bottom = getMeasuredHeight() - defaultBottomViewHeight;
            rectFDest.top = getMeasuredHeight() - bottomCoinHeight - defaultBottomViewHeight;
            rectFDest.right = rectFDest.left + bottomCoinWidth;

            rectSrc.left = 0;
            rectSrc.right = bottomCoinWidth;
            rectSrc.top = 0;
            rectSrc.bottom = bottomCoinHeight;

            canvas.drawBitmap(mBitmapBottom, rectSrc, rectFDest, mBarPaint);

            rectSrc.left = 0;
            rectSrc.right = coinWidth;
            rectSrc.top = 0;
            rectSrc.bottom = coinHeight;

            rectFDest.left = dividerWidth + (bottomCoinWidth - coinWidth) / 2;
            rectFDest.top = getMeasuredHeight() - coinHeight - defaultBottomViewHeight;
            rectFDest.right = rectFDest.left + coinWidth;
            rectFDest.bottom = getMeasuredHeight() - defaultBottomViewHeight;

            for (int j = 1; j <= currentData + 1; j++) {
                if (currentData - j < 1) {
                    rectFDest.bottom =  rectFDest.bottom - translateX * Math.abs(currentData - j);
                    rectFDest.top = rectFDest.top - translateX * Math.abs(currentData - j);
                } else {
                    rectFDest.bottom = rectFDest.bottom - translateX;
                    rectFDest.top = rectFDest.top - translateX;
                }

                if (i == 0) {
                    canvas.drawBitmap(mBitmapCenterRed, rectSrc, rectFDest, mBarPaint);
                } else {
                    canvas.drawBitmap(mBitmapCenterYellow, rectSrc, rectFDest, mBarPaint);
                }
            }
            canvas.drawBitmap(mBitmapTop, rectSrc, rectFDest, mBarPaint);
        }
        dividerWidth = 0;
    }

    /**
     * 设置数据
     */
    public void setDatas(List<ChartInfo> datas) {
        this.datas.clear();
        this.datas.addAll(datas);
        if (datas.size() > 0){
            maxApr = datas.get(0).getApr();
            for (int i = 0; i < datas.size(); i++) {
                float currentData = datas.get(i).getApr();
                if (maxApr < currentData) {
                    maxApr = currentData;
                }
            }
        }
        // 保证更新数据后，调用一次 onMeasure，避免布局被遮挡
        requestLayout();
        invalidate();
    }

    /**
     * 当本控件可见时，开始播放动画
     */
    public void setPlayAnimation(boolean playAnimation) {
        this.playAnimation = playAnimation;
        invalidate();
    }

    /**
     * 当本控件利率对应的时间期限显示出来时，开始播放动画
     * @return 利率对应的时间期限的相对控件本身的 Y 坐标
     */
    public int getTimeLimitYCoordinate() {
        return getMeasuredHeight() - defaultBottomViewHeight;
    }
}
