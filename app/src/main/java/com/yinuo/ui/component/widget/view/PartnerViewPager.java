package com.yinuo.ui.component.widget.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.yinuo.adapter.ViewpagerAdapter;
import com.yinuo.ui.component.widget.baseview.BaseRecyclerView;

import java.util.List;

/**
 * Created by gus on 16/4/23.
 */
public class PartnerViewPager extends ViewPager {

    private ViewpagerAdapter mAdapter;

    /** the last x position */
    private float mLastPosition;

    /** if the first swipe was from left to right (->), dont listen to swipes from the right */
    private boolean mSlidingLeft;

    /** if the first swipe was from right to left (<-), dont listen to swipes from the left */
    private boolean mSlidingRight;

    public PartnerViewPager(Context context) {
        this(context, null);
    }

    public PartnerViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);

        mAdapter = new ViewpagerAdapter();
    }

    public <T extends BaseRecyclerView> void setViews(List<T> views) {
        mAdapter.setViews(views);
        setAdapter(mAdapter);
    }

    @Override
    public boolean onTouchEvent(final MotionEvent ev) {
        final int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN: {

                // Disallow parent ViewPager to intercept touch events.
                this.getParent().requestDisallowInterceptTouchEvent(true);

                // save the current x position
                this.mLastPosition = ev.getX();
                break;
            }
            case MotionEvent.ACTION_UP: {
                // Allow parent ViewPager to intercept touch events.
                this.getParent().requestDisallowInterceptTouchEvent(false);

                // save the current x position
                this.mLastPosition = ev.getX();

                // reset swipe actions
                this.mSlidingLeft = false;
                this.mSlidingRight = false;

                break;
            }
            case MotionEvent.ACTION_MOVE: {
                /*
                 * if this is the first item, scrolling from left to
                 * right should navigate in the surrounding ViewPager
                 */
                if (this.getCurrentItem() == 0) {
                    // swiping from left to right (->)?
                    if (this.mLastPosition <= ev.getX() && !this.mSlidingRight) {
                        // make the parent touch interception active -> parent pager can swipe
                        this.getParent().requestDisallowInterceptTouchEvent(false);
                    } else {
                        /*
                         * if the first swipe was from right to left, dont listen to swipes
                         * from left to right. this fixes glitches where the user first swipes
                         * right, then left and the scrolling state gets reset
                         */
                        this.mSlidingRight = true;

                        // save the current x position
                        this.mLastPosition = ev.getX();
                        this.getParent().requestDisallowInterceptTouchEvent(true);
                    }
                } else
                /*
                 * if this is the last item, scrolling from right to
                 * left should navigate in the surrounding ViewPager
                 */
                    if (this.getCurrentItem() == this.getAdapter().getCount() - 1) {
                        // swiping from right to left (<-)?
                        if (this.mLastPosition >= ev.getX() && !this.mSlidingLeft) {
                            // make the parent touch interception active -> parent pager can swipe
                            this.getParent().requestDisallowInterceptTouchEvent(false);
                        } else {
                        /*
                         * if the first swipe was from left to right, dont listen to swipes
                         * from right to left. this fixes glitches where the user first swipes
                         * left, then right and the scrolling state gets reset
                         */
                            this.mSlidingLeft = true;

                            // save the current x position
                            this.mLastPosition = ev.getX();
                            this.getParent().requestDisallowInterceptTouchEvent(true);
                        }
                    }

                break;
            }
        }

        return super.onTouchEvent(ev);
    }

    @Override
    protected boolean canScroll(View v, boolean checkV, int dx, int x, int y) {
        if(v != this && v instanceof ViewPager) {
            int currentItem = ((ViewPager) v).getCurrentItem();
            int countItem = ((ViewPager) v).getAdapter().getCount();
            if((currentItem == (countItem - 1) && dx < 0) || (currentItem == 0 && dx > 0)){
                return false;
            }
            return true;
        }
        return super.canScroll(v, checkV, dx, x, y);
    }

}
