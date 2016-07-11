package com.yinuo.ui.component.baseview;

import android.content.Context;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.TextView;

/**
 * Created by ludexiang on 2016/4/15.
 */
public class BaseFloatOptionView extends TextView {
    public enum OptionDirection {LEFT, TOP, RIGHT, BOTTOM, NONE}
    protected OptionDirection mDirection = OptionDirection.NONE;
    protected RectF mAddOuter;

    protected static RectF sLeftOuter;
    protected static RectF sTopOuter;
    protected static RectF sRightOuter;
    protected static RectF sBottomOuter;
    protected IClickListener mClickListener;

    public BaseFloatOptionView(Context context) {
        this(context, null);
    }

    public BaseFloatOptionView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseFloatOptionView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setLeftOuter(RectF left) {
        sLeftOuter = new RectF(left);
    }

    public void setTopOuter(RectF top) {
        sTopOuter = new RectF(top);
    }

    public void setRightOuter(RectF right) {
        sRightOuter = new RectF(right);
    }

    public void setBottomOuter(RectF bottom) {
        sBottomOuter = new RectF(bottom);
    }

    public void setIClickListener(IClickListener listener) {
        mClickListener = listener;
    }

    public interface IClickListener {
        void onClickDown(OptionDirection direction);

        void onClickUp(OptionDirection direction);

        void closeOptions();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                int x = (int) event.getRawX();
                int y = (int) event.getRawY();
                mDirection = getDirection(x, y);
                if (mClickListener != null && mDirection != OptionDirection.NONE) {
                    mClickListener.onClickDown(mDirection);
                } else {
                    mClickListener.closeOptions();
                }
                return true;
            }
            case MotionEvent.ACTION_UP: {
                if (mClickListener != null && mDirection != OptionDirection.NONE) {
                    mClickListener.onClickUp(mDirection);
                }
                break;
            }
        }
        return super.onTouchEvent(event);
    }

    private OptionDirection getDirection(int x, int y) {
        OptionDirection direction = OptionDirection.NONE;
        if (sBottomOuter != null && sBottomOuter.contains(x, y)) {
            direction = OptionDirection.BOTTOM;
        }
        if (sTopOuter != null && sTopOuter.contains(x, y)) {
            direction = OptionDirection.TOP;
        }

        if (sLeftOuter != null && sLeftOuter.contains(x, y)) {
            direction = OptionDirection.LEFT;
        }

        if (sRightOuter != null && sRightOuter.contains(x, y)) {
            direction = OptionDirection.RIGHT;
        }
        return direction;
    }
}
