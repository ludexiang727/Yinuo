package com.yinuo.ui.component.widget.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ValueAnimator;
import com.yinuo.R;
import com.yinuo.utils.AppUtils;

/**
 * Created by didi on 16/5/26.
 */
public class LoanCalculatorGuide extends View {
    private int mScreenWidth;
    private RectF mRectF;
    private float mCircleRadius;
    private RectF mStrokeRect;
    private int mStrokeWidth;
    private float mStrokeSweep;

    private long mAfterTax;
    private long mPreTax;
    private long mBenefit;
    private long mIndividual;

    private float mAfterStart;
    private float mAfterSweep;
    private float mBenefitStart;
    private float mBenefitSweep;
    private float mIndividualStart;
    private float mIndividualSweep;

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
        mStrokeRect = new RectF(mRectF.left - mStrokeWidth, mRectF.top - mStrokeWidth,
                mRectF.right + mStrokeWidth, mRectF.bottom + mStrokeWidth);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawStroke(canvas);

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setDither(true);
        paint.setStyle(Paint.Style.FILL);

        paint.setColor(mAfterTaxColor);
        canvas.drawArc(mRectF, mAfterStart, mAfterSweep, true, paint);
//        paint.setColor(mPreTaxColor);
//        canvas.drawArc(mRectF, 135, 65, true, paint);
        paint.setColor(mBenefitColor);
        canvas.drawArc(mRectF, mBenefitStart, mBenefitSweep, true, paint);
        paint.setColor(mIndividualColor);
        canvas.drawArc(mRectF, mIndividualStart, mIndividualSweep, true, paint);

    }

    /** ps: sweepAngle 划过多少angle 偏移多少° */
    private void setSweepAngle(float sweepAngle) {
    }

    public void setPrices(long afterTax, long preTax, long benefit, long individual) {
        mAfterTax = afterTax;
        mPreTax = preTax;
        mBenefit = benefit;
        mIndividual = individual;

        startAnim();
    }

    // draw stroke 描边 也是 preTax 收入
    private void drawStroke(Canvas canvas) {
        Paint strokePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        strokePaint.setStyle(Paint.Style.STROKE);
        strokePaint.setDither(true);
        strokePaint.setStrokeWidth(5f);
        strokePaint.setColor(/*Color.parseColor("#E3E3E3")*/mPreTaxColor);
        canvas.drawArc(mStrokeRect, 180f, mStrokeSweep, false, strokePaint);
    }

    private Animator strokeAnim() {
        ValueAnimator strokeAnim = ValueAnimator.ofFloat(0f, -360f);
        strokeAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Float sweep = (Float) animation.getAnimatedValue();
                mStrokeSweep = sweep.floatValue();
            }
        });
        return strokeAnim;
    }

    private Animator arcAnim(final int type, final float from, final float to) {
        ValueAnimator arcAnim = ValueAnimator.ofFloat(0f, to);
        arcAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Float arcValue = (Float) animation.getAnimatedValue();
                if (type == 0) {
                    mAfterStart = from;
                    mAfterSweep = arcValue.floatValue();
                } else if (type == 1) {
                    mBenefitStart = from;
                    mBenefitSweep = arcValue.floatValue();
                } else if (type == 2) {
                    mIndividualStart = from;
                    mIndividualSweep = arcValue.floatValue();
                }
                postInvalidate();
            }
        });
        return arcAnim;
    }

    private void startAnim() {
        AnimatorSet set = new AnimatorSet();
        Animator stroke = strokeAnim();

        AnimatorSet arcSet = new AnimatorSet();
        Animator arcAfterTax = arcAnim(0, 0f, mAfterTax);
        Animator arcBenefit = arcAnim(1, mAfterTax, mBenefit);
        Animator arcIndividual = arcAnim(2, mAfterTax + mBenefit, mIndividual);
        arcSet.playSequentially(arcAfterTax, arcBenefit, arcIndividual);

        set.playTogether(stroke, arcSet);
        set.setDuration(1500);
        set.setInterpolator(new LinearInterpolator());
        set.start();
    }

    public void reset() {
        mStrokeSweep = 0f;
        mAfterStart = 0f;
        mAfterSweep = 0f;
        mBenefitStart = 0f;
        mBenefitSweep = 0f;
        mIndividualStart = 0f;
        mIndividualSweep = 0f;
        postInvalidate();
    }
}
