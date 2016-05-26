package com.yinuo.ui.component.widget.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.yinuo.R;
import com.yinuo.utils.AppUtils;

/**
 * Created by didi on 16/5/26.
 */
public class LoanCalculatorGuide extends View {
    private int mScreenWidth;
    private RectF mRectF;
    private float mCircleRadius;
    private float mStartSweep = 0f;
    private float mSweepAngle;
    private RectF mStrokeRect;
    private int mStrokeWidth;

    private int mAfterTaxColor;
    private int mPreTaxColor;
    // benefit -- 保险金
    private int mBenefitColor;
    // individual -- 个税
    private int mIndividualColor;

    public LoanCalculatorGuide(Context context) {
        this(context, null);
    }

    public LoanCalculatorGuide(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoanCalculatorGuide(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.LoanCalculatorGuide);
        mStrokeWidth = a.getDimensionPixelOffset(R.styleable.LoanCalculatorGuide_guide_stroke, 2);
        mCircleRadius = a.getDimensionPixelOffset(R.styleable.LoanCalculatorGuide_circle_radius, 0);
        mAfterTaxColor = a.getColor(R.styleable.LoanCalculatorGuide_after_tax_color, 0);
        mPreTaxColor = a.getColor(R.styleable.LoanCalculatorGuide_pre_tax_color, 0);
        mBenefitColor = a.getColor(R.styleable.LoanCalculatorGuide_insurance_benefit_color, 0);
        mIndividualColor = a.getColor(R.styleable.LoanCalculatorGuide_individual_tax, 0);
        a.recycle();
        setWillNotDraw(false);
        mScreenWidth = AppUtils.obtainScreenWidthAndHeight(context)[0];
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (mCircleRadius == 0) {
            return;
        }
        setMeasuredDimension(mScreenWidth / 2, (int) mCircleRadius * 3);
        mRectF = new RectF(90, 30, 390, 330);
        mStrokeRect = new RectF(mRectF.left, mRectF.top,
                mRectF.right + mStrokeWidth, mRectF.bottom + mStrokeWidth);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint strokePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        strokePaint.setStyle(Paint.Style.STROKE);
        strokePaint.setDither(true);
        strokePaint.setStrokeWidth(5f);
        strokePaint.setColor(Color.parseColor("#E3E3E3"));
        canvas.drawCircle(mRectF.left + mRectF.width() / 2, mRectF.top + mRectF.height() / 2, mStrokeRect.height() / 2, strokePaint);

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setDither(true);
        paint.setStyle(Paint.Style.FILL);

        paint.setColor(mAfterTaxColor);
        canvas.drawArc(mRectF, mStartSweep, 135, true, paint);
        paint.setColor(mPreTaxColor);
        canvas.drawArc(mRectF, 135, 65, true, paint);
        paint.setColor(mBenefitColor);
        canvas.drawArc(mRectF, 200, 90, true, paint);
        paint.setColor(mIndividualColor);
        canvas.drawArc(mRectF, 290, 70, true, paint);

    }

    /**
     * ps: sweepAngle 划过多少angle
     */
    public void setSweepAngle(float sweepAngle) {
        mSweepAngle = sweepAngle;
    }
}
