package com.yinuo.ui.component.widget.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.TextView;

import com.yinuo.R;
import com.yinuo.utils.ResUtils;

/**
 * Created by ludexiang on 2016/4/20.
 */
public class WorkspaceTagsView extends TextView {

    private int mPadding;

    public WorkspaceTagsView(Context context) {
        this(context, null);
    }

    public WorkspaceTagsView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WorkspaceTagsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mPadding = ResUtils.getInt(context, R.dimen.workspace_page_tag_padding);
        setBackgroundResource(R.drawable.workspace_page_tags_bg);
        setPadding(mPadding, mPadding, mPadding, mPadding);

        setTextSize(TypedValue.COMPLEX_UNIT_PX, ResUtils.getInt(context, R.dimen.workspace_page_tag_size));
    }

}
