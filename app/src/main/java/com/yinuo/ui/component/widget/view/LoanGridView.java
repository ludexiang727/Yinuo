package com.yinuo.ui.component.widget.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.GridView;

import com.yinuo.R;
import com.yinuo.adapter.LoanGridViewAdapter;
import com.yinuo.mode.LoanGridViewModel;

import java.util.List;

/**
 * Created by ludexiang on 2016/4/19.
 */
public class LoanGridView extends GridView {

    private LoanGridViewAdapter mAdapter;
    private Paint mPaint;
    private int mPaintColor;
    private int mPaintWidth;

    public LoanGridView(Context context) {
        this(context, null);
    }

    public LoanGridView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoanGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.LoanGridView);

        mPaintColor = a.getColor(R.styleable.LoanGridView_separator_color, 0);
        mPaintWidth = a.getDimensionPixelOffset(R.styleable.LoanGridView_separator_width, 1);

        a.recycle();

        mAdapter = new LoanGridViewAdapter(context);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(mPaintColor);
        mPaint.setStrokeWidth(mPaintWidth);
    }

    public void setOptions(List<LoanGridViewModel> modelList) {
        mAdapter.setItems(modelList);
        setAdapter(mAdapter);
    }

    public LoanGridViewAdapter getLoanAdapter() {
        return mAdapter;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
    }
}
