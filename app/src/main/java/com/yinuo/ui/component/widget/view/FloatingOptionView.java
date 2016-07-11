package com.yinuo.ui.component.widget.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.util.AttributeSet;

import com.yinuo.R;
import com.yinuo.ui.component.baseview.BaseFloatOptionView;
import com.yinuo.utils.AppUtils;
import com.yinuo.utils.ResUtils;


/**
 * Created by ludexiang on 2016/4/13.
 */
public class FloatingOptionView extends BaseFloatOptionView {
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
    private Paint mPathPaint = new Paint();
    private int mFloatBtnWidth;
    private int mFloatBtnMargin;

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

        mPathPaint.setAntiAlias(true);
        mPathPaint.setStyle(Paint.Style.FILL);
        mPathPaint.setColor(Color.parseColor("#ccff4081"));
        mPathPaint.setTextSize(ResUtils.getInt(getContext(), R.dimen.home_page_detail_options_size));
    }

    public void setCenter(float centerX, float centerY, int floatWidth, int margin) {
        mCenterX = centerX;
        mCenterY = centerY;
        mFloatBtnWidth = floatWidth;
        mFloatBtnMargin = margin;
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
        mAddOuter = new RectF(outer.left - 2, outer.top - 2, outer.right + 2, outer.bottom + 2);
        postInvalidate();
    }

    public void setDrawText(String text) {
        mDrawText = text;
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        Path path = new Path();

        if (mAddOuter != null && !mAddOuter.isEmpty()) {
            canvas.clipRect(mAddOuter);
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
        } else if (mDircetion == OptionDirection.RIGHT) {
            // right
            drawArc(canvas, 330f, 60f, mInner, mInnerPaint, false);
            drawArc(canvas, 330f, 60f, mOuter, mOuterPaint, true);
            drawArc(canvas, 330f, 60f, mInner, mStrokePaint, false);
            drawArc(canvas, 330f, 60f, mOuter, mStrokePaint, true);
            path.addArc(mMiddleRectF, 330f, 330f);
        } else if (mDircetion == OptionDirection.BOTTOM) {
            // bottom
            drawArc(canvas, 60f, 60f, mInner, mInnerPaint, false);
            drawArc(canvas, 60f, 60f, mOuter, mOuterPaint, true);
            drawArc(canvas, 60f, 60f, mInner, mStrokePaint, false);
            drawArc(canvas, 60f, 60f, mOuter, mStrokePaint, true);
            path.addArc(mMiddleRectF, 60f, 60f);
        } else if (mDircetion == OptionDirection.TOP) {
            drawArc(canvas, 240f, 60f, mInner, mInnerPaint, false);
            drawArc(canvas, 240f, 60f, mOuter, mOuterPaint, true);
            drawArc(canvas, 240f, 60f, mInner, mStrokePaint, false);
            drawArc(canvas, 240f, 60f, mOuter, mStrokePaint, true);
            path.addArc(mMiddleRectF, 240f, 60f);
        }
        drawText(canvas, mDrawText, path, mPathPaint);
    }

    private void drawArc(Canvas canvas, float startAngle, float sweepAngle, RectF rectF, Paint paint, boolean useCenter) {
        canvas.drawArc(rectF, startAngle, sweepAngle, useCenter, paint);
//        canvas.drawRect(mInner, paint);
    }



    private void drawLine(Path path) {
        mPathMeasure.setPath(path, true);
        float distance = mPathMeasure.getLength();
        mPathMeasure.getPosTan(distance, mPointCoor, null);
    }

    public void drawText(Canvas canvas, String text, Path path, Paint paint) {
        float width = paint.measureText(text, 0, text.length());
        if (width > 100) {
            mPathPaint.setTextSize(ResUtils.getInt(getContext(), R.dimen.home_page_detail_options_txt_long));
            canvas.drawTextOnPath(text, path, 20, 20, paint);
        } else {
            canvas.drawTextOnPath(text, path, 40, 20, paint);
        }
    }

    public void setPaintColor(int color, int txtColor) {
        mOuterPaint.setColor(color);
        mPathPaint.setColor(txtColor);
    }

}
