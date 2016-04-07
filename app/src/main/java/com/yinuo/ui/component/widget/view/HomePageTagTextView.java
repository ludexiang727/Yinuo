package com.yinuo.ui.component.widget.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.yinuo.R;

/**
 * Created by ludexiang on 2016/4/7.
 */
public class HomePageTagTextView extends TextView {
    public enum TagBackGroundType {
        GREEN, RED, BLUE, YELLOW, NONE
    }

    private TagBackGroundType mTagBackGroundType = TagBackGroundType.NONE;

    public HomePageTagTextView(Context context) {
        this(context, null);
    }

    public HomePageTagTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HomePageTagTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void setBackground(TagBackGroundType type) {
        switch(type) {
            case RED: {
                setBackgroundResource(R.drawable.home_page_tag_red_bg);
                break;
            }
            case GREEN: {
                setBackgroundResource(R.drawable.home_page_tag_green_bg);
                break;
            }
            case BLUE: {
                setBackgroundResource(R.drawable.home_page_tag_blue_bg);
                break;
            }
            case YELLOW: {
                setBackgroundResource(R.drawable.home_page_tag_yellow_bg);
                break;
            }
            default: {
                setBackgroundResource(R.drawable.home_page_tag_none_bg);
                break;
            }
        }
    }

    public void setText(String text, TagBackGroundType type) {
        super.setText(text);
        setBackground(type);
    }
}
