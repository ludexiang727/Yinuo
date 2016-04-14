package com.yinuo.ui.component.widget.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

import com.yinuo.R;
import com.yinuo.utils.AppUtils;
import com.yinuo.utils.ResUtils;


/**
 * Created by ludexiang on 2016/4/13.
 */
public class FloatingOptionView extends TextView {

    public enum OptionDirection {LEFT, TOP, RIGHT, BOTTOM}
    private OptionDirection mDircetion = OptionDirection.LEFT;
    private int[] mLocation;
    private RectF mInner;
    private RectF mMiddleRectF;
    private RectF mOuter;
    private float mInnerRadius;
    private float mOuterRadius;
    private float mCenterX;
    private float mCenterY;
    private int mScreenWidth;
    private PathMeasure mPathMeasure;
    private float[] mPointCoor = new float[2];
    private Paint mStrokePaint = new Paint();
    private Paint mInnerPaint = new Paint();
    private Paint mOuterPaint = new Paint();

    private String mDrawText;

    public FloatingOptionView(Context context) {
        this(context, null);
    }

    public FloatingOptionView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FloatingOptionView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mScreenWidth = AppUtils.obtainScreenWidthAndHeight(getContext())[0];
        mPathMeasure = new PathMeasure();

        mStrokePaint.setAntiAlias(true);
        mInnerPaint.setAntiAlias(true);
        mOuterPaint.setAntiAlias(true);

        mStrokePaint.setColor(Color.parseColor("#ff4081"));
        mStrokePaint.setStrokeWidth(1);
        mStrokePaint.setStyle(Paint.Style.STROKE);

        mInnerPaint.setStyle(Paint.Style.STROKE);
        mInnerPaint.setColor(Color.parseColor("#ccffffff"));
        mInnerPaint.setStrokeWidth(1.5f);

        mOuterPaint.setStyle(Paint.Style.FILL);
        mOuterPaint.setColor(Color.parseColor("#ccffffff"));
        mOuterPaint.setStrokeWidth(1.5f);
    }

    public void setCenter(float centerX, float centerY) {
        mCenterX = centerX;
        mCenterY = centerY;
    }

    public void setOptionDirection(OptionDirection direction) {
        mDircetion = direction;
    }

    public void setInner(RectF inner) {
        mInner = new RectF(inner);
    }

    public void setMiddleRectF(RectF middle) {
        mMiddleRectF = middle;
    }

    public void setOuter(RectF outer) {
        mOuter = new RectF(outer);
        postInvalidate();
    }

    public void setDrawText(String text) {
        mDrawText = text;
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);

        Path path = new Path();
        Paint pathPaint = new Paint();
        pathPaint.setAntiAlias(true);
        pathPaint.setStyle(Paint.Style.FILL);
        pathPaint.setColor(Color.parseColor("#ccff4081"));
        pathPaint.setTextSize(ResUtils.getInt(getContext(), R.dimen.home_page_detail_options_size));
        if (mOuter != null && !mOuter.isEmpty()) {
            canvas.clipRect(mOuter);
        }
        /**
         * oval 设置位置
         * startAngle 起始度数
         * sweepAngle 画多少度的弧
         * useCenter 是否与中心点连线 false 不连
         * paint 画笔
         *canvas.drawArc(RectF oval, float startAngle, float sweepAngle, boolean useCenter, Paint paint)
         */
        if (mDircetion == OptionDirection.LEFT) {
            // left
            drawArc(canvas, 150f, 60f, mInner, mInnerPaint, false);
            drawArc(canvas, 150f, 60f, mOuter, mOuterPaint, true);
            drawArc(canvas, 150f, 60f, mInner, mStrokePaint, false);
            drawArc(canvas, 150f, 60f, mOuter, mStrokePaint, true);
            path.addArc(mMiddleRectF, 150f, 60f);
            drawText(canvas, mDrawText, path, pathPaint);
            Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
            p.setColor(Color.BLACK);
            p.setStyle(Paint.Style.FILL);
            p.setStrokeWidth(5f);
            canvas.drawPoint(mCenterX, mCenterY, p);
        } else if (mDircetion == OptionDirection.RIGHT) {
            // right
            drawArc(canvas, 330f, 60f, mInner, mInnerPaint, false);
            drawArc(canvas, 330f, 60f, mOuter, mOuterPaint, true);
            drawArc(canvas, 330f, 60f, mInner, mStrokePaint, false);
            drawArc(canvas, 330f, 60f, mOuter, mStrokePaint, true);
        } else if (mDircetion == OptionDirection.BOTTOM) {
            // bottom
            drawArc(canvas, 60f, 60f, mInner, mInnerPaint, false);
            drawArc(canvas, 60f, 60f, mOuter, mOuterPaint, true);
            drawArc(canvas, 60f, 60f, mInner, mStrokePaint, false);
            drawArc(canvas, 60f, 60f, mOuter, mStrokePaint, true);
            path.addArc(mMiddleRectF, 60f, 60f);
            drawText(canvas, mDrawText, path, pathPaint);
        } else if (mDircetion == OptionDirection.TOP) {
            drawArc(canvas, 240f, 60f, mInner, mInnerPaint, false);
            drawArc(canvas, 240f, 60f, mOuter, mOuterPaint, true);
            drawArc(canvas, 240f, 60f, mInner, mStrokePaint, false);
            drawArc(canvas, 240f, 60f, mOuter, mStrokePaint, true);
        }
    }

    private void drawArc(Canvas canvas, float startAngle, float sweepAngle, RectF rectF, Paint paint, boolean useCenter) {
        canvas.drawArc(rectF, startAngle, sweepAngle, useCenter, paint);
    }

    private void drawLine(Path path) {
        mPathMeasure.setPath(path, true);
        float distance = mPathMeasure.getLength();
        mPathMeasure.getPosTan(distance, mPointCoor, null);
    }

    public void drawText(Canvas canvas, String text, Path path, Paint paint) {
        canvas.drawTextOnPath(text, path, 40, 20, paint);
    }
}
