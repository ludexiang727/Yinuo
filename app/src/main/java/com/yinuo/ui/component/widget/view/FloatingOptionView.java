package com.yinuo.ui.component.widget.view;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;


/**
 * Created by ludexiang on 2016/4/13.
 */
public class FloatingOptionView extends View {

    public enum OptionDirection {LEFT, TOP, RIGHT, BOTTOM}
    private OptionDirection mDircetion = OptionDirection.LEFT;
    private int[] mLocation;

    public FloatingOptionView(Context context) {
        this(context, null);
    }

    public FloatingOptionView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FloatingOptionView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setOptionDirection(OptionDirection direction) {
        mDircetion = direction;
    }

    public void setFloatActionLocation(int[] location) {
        mLocation = location;
        Log.e("ldx", "floation........." + location[0] + " " + location[1]);
    }
}
