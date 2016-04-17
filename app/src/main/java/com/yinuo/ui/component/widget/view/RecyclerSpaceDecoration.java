package com.yinuo.ui.component.widget.view;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by gus on 16/4/17.
 */
public class RecyclerSpaceDecoration extends RecyclerView.ItemDecoration {
    private Rect mCardPadding;


    public RecyclerSpaceDecoration() {
        mCardPadding = new Rect(10, 10, 10, 10);
    }


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect = mCardPadding;
        outRect.left = 20;
        outRect.right = 20;
        outRect.bottom = 20;
        if (parent.getChildAdapterPosition(view) == 0) {
            outRect.top = mCardPadding.top;
        }
    }
}
