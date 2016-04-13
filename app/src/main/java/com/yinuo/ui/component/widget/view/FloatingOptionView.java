package com.yinuo.ui.component.widget.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

import com.yinuo.utils.AppUtils;


/**
 * Created by ludexiang on 2016/4/13.
 */
public class FloatingOptionView extends TextView {

    public enum OptionDirection {LEFT, TOP, RIGHT, BOTTOM}
    private OptionDirection mDircetion = OptionDirection.LEFT;
    private int[] mLocation;
    private RectF mInner;
    private RectF mOuter;
    private float mInnerRadius;
    private float mOuterRadius;
    private float mCenterX;
    private float mCenterY;
    private int mScreenWidth;


    public FloatingOptionView(Context context) {
        this(context, null);
    }

    public FloatingOptionView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FloatingOptionView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mScreenWidth = AppUtils.obtainScreenWidthAndHeight(getContext())[0];
    }

    public void setOptionDirection(OptionDirection direction) {
        mDircetion = direction;
    }

    public void setFloatActionLocation(int[] location, int floatWidth, int margin) {
        mLocation = location;
        mCenterX = location[0] + floatWidth / 2;
        mCenterY = location[1] + floatWidth / 2;
        mInnerRadius = mScreenWidth - mCenterX + margin;
    }

    public void setInner(RectF inner) {
        mInner = new RectF(inner);
        postInvalidate();
    }

    public void setOuter(RectF outer) {
        mOuter = new RectF(outer);
        postInvalidate();
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);

        Path path = new Path();
        Log.e("ldx", "draw .... " + mInner.left + " top " + mInner.top + " right " + mInner.right + " bottom " + mInner.bottom + " inner radius " + mInnerRadius);
        Log.e("ldx", "mCenterX .... " + mCenterX + " mCenterY " + mCenterY);
        if (mDircetion == OptionDirection.LEFT) {
            // left
//            canvas.drawArc(mInner, -150f, -60f, false, paint);
//            canvas.drawArc(mOuter, -150f, -60f, false, paint);
            path.addArc(mInner, -150f, -60f);
            path.addArc(mOuter, -150f, -60f);

        } else if (mDircetion == OptionDirection.RIGHT) {
            // right
            canvas.drawArc(mInner, -30f, 60f, false, paint);
            canvas.drawArc(mOuter, -30f, 60f, false, paint);
        } else if (mDircetion == OptionDirection.BOTTOM) {
            // bottom
//            canvas.drawArc(mInner, 120f, -60f, false, paint);
//            canvas.drawArc(mOuter, 118f, -62f, false, paint);

            path.addArc(mInner, 120f, -60f);
            path.addArc(mOuter, 118f, -62f);
        } else if (mDircetion == OptionDirection.TOP) {
            canvas.drawArc(mInner, -120f, 60f, false, paint);
            canvas.drawArc(mOuter, -120f, 60f, false, paint);
        }
        canvas.drawPath(path, paint);
//        canvas.drawRect(mInner, paint);
//        canvas.drawPoint(mCenterX, mCenterY, paint);
    }

    public void drawText() {

    }
}
