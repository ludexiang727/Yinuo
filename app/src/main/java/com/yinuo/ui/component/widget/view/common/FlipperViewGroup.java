package com.yinuo.ui.component.widget.view.common;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Scroller;

import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.yinuo.base.BaseObject;
import com.yinuo.helper.ImageLoaderHelper;

public class FlipperViewGroup extends ViewGroup implements Runnable {
	/** viewgroup measure width & height */
	private int mParentWidth;
	private int mParentHeight;

	/** viewgroup scroller */
	private Scroller mScroller;

	/** velocity tracker */
	private VelocityTracker mVelocityTracker;

	private float mDownX;
	private float mDownY;
	private float mLastDownX;
	private float mLastDownY;
	private final static int INVALID_SCREEN = -1;
	private final static int TOUCH_STATE_REST = 0;
	private final static int TOUCH_STATE_SCROLLING = 1;
	private static final int SNAP_VELOCITY = 600;
	private static final float BASELINE_FLING_VELOCITY = 2500.f;


	private int mTouchState = TOUCH_STATE_REST;
	private int mCurrentScreen = 0;
	private int mNextScreen = INVALID_SCREEN;

	private int mDirection;
	private int mTouchSlop;
	private int mMaximumVelocity;
	private int mSnapSlop = 100;
	private boolean mNotInterceptFlag = false;
	private boolean mAllowLongPress = true;

	// mark current image size , now is test so mSize is fixed
	private int mSize = 0;
	private ScreenHelper mScreenHelper;
	private Duration tDuration = Duration.DURATION_NONE;

	private enum Duration {
		DURATION_LEFT, DURATION_RIGHT, DURATION_NONE
	}

	private Duration duration = Duration.DURATION_NONE;

	private IndicatorView mIndicatorView;
	private boolean autoFlipping = false;
	private static final int SLEEP_TIME = 500;
	private Thread autoFlippingThread = new Thread(this);
	private boolean isLayout = false;
    private IClickListener mClickListener;

	public FlipperViewGroup(Context context) {
		this(context, null);
	}

	public FlipperViewGroup(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public FlipperViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);

		mScroller = new Scroller(context);
		ViewConfiguration config = ViewConfiguration.get(context);
		mTouchSlop = config.getScaledTouchSlop();
		mMaximumVelocity = config.getScaledMaximumFlingVelocity();
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int width = MeasureSpec.getSize(widthMeasureSpec);
		int height = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
		int childCount = getChildCount();
        Log.e("ldx", "width " + width + " height " + height);
		mSize = childCount;
		for (int i = 0; i < mSize; ++i) {
			getChildAt(i).measure(widthMeasureSpec, heightMeasureSpec);
		}

		setMeasuredDimension(width, height);
        if (mIndicatorView != null) {
            // app detail page without indicator
            mIndicatorView.addDotView(mSize);
            mIndicatorView.snapToScreen(mCurrentScreen);
        }
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		if (!isLayout) {
			isLayout = true;
			int childCount = getChildCount();
			mParentWidth = getMeasuredWidth();
			mParentHeight = getMeasuredHeight();

			mSnapSlop = (Math.min(mParentWidth, mParentHeight) >> 1);

			List<FlipperView> childsView = new ArrayList<FlipperView>();
			for (int i = 0; i < childCount; ++i) {
				FlipperView flipper = new FlipperView(getChildAt(i), i, i * mParentWidth, t);
				childsView.add(flipper);
			}

			mScreenHelper = new ScreenHelper(childsView);
			mScreenHelper.updateLayout();
		}
	}

	public View getCurrentScreen() {
		return getChildAt(mCurrentScreen);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		if (mSize <= 1) {
			return super.onInterceptTouchEvent(ev);
		}
		/**
		 * This method JUST determines whether we want to intercept the motion.
		 * If we return true, onTouchEvent will be called and we do the actual
		 * scrolling there.
		 */
		final int action = (ev.getAction() & MotionEvent.ACTION_MASK);
		if ((action == MotionEvent.ACTION_MOVE) && (mTouchState != TOUCH_STATE_REST)) {
			return true;
		}

		addVelocityTracker(ev);
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			if (mScroller.isFinished()) {
				mScroller.abortAnimation();
			}
			mDownX = mLastDownX = (int) ev.getX();
			mDirection = 0;
			mNotInterceptFlag = false;
			mAllowLongPress = true;

			mTouchState = (!mScroller.isFinished() ? TOUCH_STATE_REST : TOUCH_STATE_SCROLLING);

			isAllowOnInterceptTouchEvent(true);
			autoFlipping = false;
			
			break;
		case MotionEvent.ACTION_CANCEL:
		case MotionEvent.ACTION_UP:
			// if there are no move event occurred between down and up
			// event.
			mTouchState = TOUCH_STATE_REST;
			mAllowLongPress = false;
			
			recycleTracker();
			isAllowOnInterceptTouchEvent(false);
			break;
		case MotionEvent.ACTION_MOVE:
			isAllowOnInterceptTouchEvent(true);
			final float x = ev.getX();
			final float y = ev.getY();
			final int xDiff = (int) Math.abs(x - mLastDownX);
			final int yDiff = (int) Math.abs(y - mLastDownY);

			final int touchSlop = mTouchSlop;
			boolean xMoved = xDiff > touchSlop;
			boolean yMoved = yDiff > touchSlop;

			if ((yDiff << 1) < xDiff) {
				if (xMoved) { // 启动水平方向的滑动
					mScreenHelper.changeMode(mCurrentScreen, ScreenHelper.ModeHorizontal);
					mTouchState = TOUCH_STATE_SCROLLING;

					if (!mScroller.isFinished())
						mScroller.abortAnimation();
				}
			}

			if (mTouchState == TOUCH_STATE_SCROLLING && mAllowLongPress) {
				mAllowLongPress = false;
				getChildAt(mCurrentScreen).cancelLongPress();
			}

			break;
		} // End of switch

		return mTouchState != TOUCH_STATE_REST;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		/*
		 * MotionEvent here is for whole screen, not the Workspace. So we should
		 * use relative coordinate instead of global here.
		 */

		addVelocityTracker(event);
		

		final int action = (event.getAction() & MotionEvent.ACTION_MASK);
		switch (action) {
			case MotionEvent.ACTION_DOWN: {
				/*
				 * See ACTION_DOWN process: When mScroller is not finished, it
				 * returns true to intercept this touch event. Then, it reaches
				 * here. Set mNotInterceptFlag true to release flow move events.
				 */
				mDownX = mLastDownX = (int) event.getX();
				mDownY = mLastDownY = (int) event.getRawY();
				mNotInterceptFlag = true;

				isAllowOnInterceptTouchEvent(true);
				return true;
			}
			case MotionEvent.ACTION_MOVE: {
				if (mTouchState == TOUCH_STATE_SCROLLING) {
					handleHorizontalMoveEvent(event.getX());
				}
				isAllowOnInterceptTouchEvent(true);
				break;
			}
			case MotionEvent.ACTION_CANCEL:
			case MotionEvent.ACTION_UP: {
				if (mTouchState == TOUCH_STATE_SCROLLING) {
					if (mNotInterceptFlag) {
						if (!mScroller.isFinished()) {
							mScroller.abortAnimation();
						}
	
						handleHorizontalUpEvent();
					}
	
					recycleTracker();
				}
	
				mTouchState = TOUCH_STATE_REST;
				isAllowOnInterceptTouchEvent(false);
				autoFlipping = true;
				float upX = event.getRawX();
				float upY = event.getRawY();
				if ((upX - mLastDownX) <= mTouchSlop && (upY - mLastDownY) <= mTouchSlop) {
                    if (mClickListener != null) {
                        mClickListener.onClick(mCurrentScreen);
                    }
				}
			}
		}

		return super.onTouchEvent(event);
	}

	@Override
	public void computeScroll() {
		if (mScroller.computeScrollOffset()) {
			scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
			if (Math.abs(getScrollX()) >= mParentWidth / 2) {
				if (mIndicatorView != null) {
					mIndicatorView.snapToScreen(mNextScreen);
				}
			}
			postInvalidate();
		} else if (mNextScreen != INVALID_SCREEN) {
			mCurrentScreen = mNextScreen;
			mNextScreen = INVALID_SCREEN;

			tDuration = Duration.DURATION_NONE;
			/*
			 * Because mScroller has the max and min values, it should reset
			 * mScroller when reach max or min.
			 */
		}

	}

	private void handleHorizontalMoveEvent(float x) {
		final int deltaX = (int) (mLastDownX - x);
		if (Math.abs(deltaX) < mTouchSlop)
			return;

		// direction 左为负，右为正 (初始化mDirection的方向为与滑动方向相反)
		if (mDirection == 0)
			mDirection = deltaX;

		// deltaX > 0, 即lastX在X的右侧，表明此时手指在向左滑动
		// 所以，scroll指针要向右移动

		if (x < mDownX) {
			if (tDuration == Duration.DURATION_NONE) {
				duration = Duration.DURATION_LEFT;
			}
			// 页面向左滑动，要判断右侧是否会有缺页
			checkAndHandleBackPageFault();
		} else {
			if (tDuration == Duration.DURATION_NONE) {
				duration = Duration.DURATION_RIGHT;
			}
			checkAndHandleFrontPageFault();
		}

		// 经过之前对页面是否缺页的维护，此处可以放心的scroll了。
		// scrollY 应该是当前view的纵向对其。注意这里是scollBy，而不是scrollTo
		scrollBy(deltaX, 0);

		mLastDownX = x;
	}

	private void handleHorizontalUpEvent() {
		if (!mScroller.isFinished()) {
			return;
		}

		final VelocityTracker velocityTracker = mVelocityTracker;
		velocityTracker.computeCurrentVelocity(1000, mMaximumVelocity);

		final int velocityX = (int) Math.abs(velocityTracker.getXVelocity());
		final int scrollX = getScrollX();
		int newX = mScreenHelper.getChildById(mCurrentScreen).left;
		final int offsetX = Math.abs(newX - scrollX);
		mNextScreen = mCurrentScreen;
		/*
		 * 根据velocityX和offsetX，即滑动速度和距离判断判断是否滑动到下一屏； 根据scrollX和newX的相对位置判断滑动方向；
		 */
		if (offsetX > mSnapSlop || velocityX > SNAP_VELOCITY) {
			if (duration == Duration.DURATION_LEFT) {
				mNextScreen += 1;
				if (mNextScreen >= getChildCount()) {
					mNextScreen = 0;
				}
			} else if (duration == Duration.DURATION_RIGHT) {
				mNextScreen = mCurrentScreen - 1;
				if (mNextScreen < 0) {
					mNextScreen = getChildCount() - 1;
				}
			}
			if (newX >= scrollX) {
				// mNextScreen将可能在左侧，因为curLeft在对齐点scrollX的右侧
				newX -= mParentWidth;
			} else {
				// mNextScreen将可能在右侧，因为curLeft在对齐点scrollX的左侧
				newX += mParentWidth;
			}
		}

		/*
		 * duration与距离offsetX和速度velocityX相关， 距离越大duration越长，速度越快duration越短。
		 */
		final int delta = newX - scrollX;
		int duration = (Math.abs(delta) << 1);
		if (velocityX > BASELINE_FLING_VELOCITY) {
			duration *= BASELINE_FLING_VELOCITY / velocityX;
		}
		mScroller.startScroll(scrollX, getScrollY(), delta, 0/* , duration */);
		invalidate();
	}

	private void checkAndHandleBackPageFault() {
		if (mDirection > 0) {
			mDirection = -1;

			mScreenHelper.checkAndHandleBackPageFault(mCurrentScreen);

		}
	}

	private void checkAndHandleFrontPageFault() {
		if (mDirection < 0) {
			mDirection = 1;

			mScreenHelper.checkAndHandleFrontPageFault(mCurrentScreen);
		}
	}

	private void snapToScreen(int direction) {
		if (!mScroller.isFinished()) {
			mScroller.forceFinished(true);
		}
		if (mScreenHelper.getMode() == ScreenHelper.ModeVertical) {
			mScreenHelper.changeMode(mCurrentScreen, ScreenHelper.ModeHorizontal);
		}

		mNextScreen = mCurrentScreen;

		if (direction >= 0) {
			duration = Duration.DURATION_LEFT;
			mScreenHelper.checkAndHandleBackPageFault(mCurrentScreen);
		} else {
			duration = Duration.DURATION_RIGHT;
			mScreenHelper.checkAndHandleFrontPageFault(mCurrentScreen);
		}

		tDuration = duration;
		if (duration == Duration.DURATION_LEFT) {
			mNextScreen += 1;
			if (mNextScreen >= getChildCount()) {
				mNextScreen = 0;
			}
		} else if (duration == Duration.DURATION_RIGHT) {
			mNextScreen = mCurrentScreen - 1;
			if (mNextScreen < 0) {
				mNextScreen = getChildCount() - 1;
			}
		}

		final int delta = mScreenHelper.getChildById(mNextScreen).left - getScrollX();
		mScroller.startScroll(getScrollX(), getScrollY(), delta, 0, Math.abs(delta) * 2);
		invalidate();
	}

	private final class FlipperView {
		public int id;
		public View view;

		public int left;
		public int top;

		public FlipperView(View v, int id, int left, int top) {
			this.id = id;
			this.view = v;
			set(left, top);
		}

		public final void set(int left, int top) {
			this.left = left;
			this.top = top;
		}

		public final int getRight() {
			return (left + mParentWidth);
		}

		public final int getBottom() {
			return (top + mParentHeight);
		}

		public final void layout() {
			view.layout(left, top, getRight(), getBottom());
			view.postInvalidate();
		}

		@Override
		public String toString() {
			StringBuilder b = new StringBuilder();
			b.append(left);
			b.append(',');
			b.append(top);
			b.append(',');
			b.append(getRight());
			b.append(',');
			b.append(getBottom());
			return b.toString();
		}
	}

	public final class ScreenHelper {
		public static final int ModeHorizontal = 0;
		public static final int ModeVertical = 1;

		private int mMode = ModeHorizontal;
		private FlipperView fScreen;
		private FlipperView bScreen;

		private List<FlipperView> childLists = new ArrayList<FlipperView>();

		public ScreenHelper(List<FlipperView> screens) {
			childLists.addAll(screens);
		}

		public ScreenHelper(FlipperView s1, FlipperView s2) {
			fScreen = s1;
			bScreen = s2;
		}

		public final int getMode() {
			return mMode;
		}

		public synchronized int changeMode(int screenId, int toMode) {
			if (mMode != toMode)
				changeMode(screenId);
			return toMode;
		}

		public void updateLayout() {
			for (FlipperView screen : childLists) {
				screen.layout();
			}
		}

		// 向右滑动
		public synchronized void checkAndHandleFrontPageFault(int curScreenId) {
			moveLastToFront(curScreenId);
		}

		/**
		 * 向左滑动
		 *
		 * @param curScreenId
		 */
		public synchronized void checkAndHandleBackPageFault(int curScreenId) {
			moveFirstToBack(curScreenId);
		}

		public final FlipperView getChildById(int screenId) {
			return childLists.get(screenId);
		}

		private final void moveLastToFront(int curScreen) {
			int which = curScreen - 1;
			if (which < 0) {
				which = childLists.size() - 1;
			}
			FlipperView currentScreen = childLists.get(curScreen);
			FlipperView privousScreen = childLists.get(which);

			privousScreen.left = currentScreen.left - mParentWidth;
			privousScreen.layout();

			FlipperView tp = currentScreen;
			currentScreen = privousScreen;
			privousScreen = tp;
		}

		private final void moveFirstToBack(int curScreen) {
			int whichScreen = curScreen + 1;
			if (whichScreen >= getChildCount()) {
				whichScreen = 0;
			}
			FlipperView currentScreen = childLists.get(curScreen);
			FlipperView nextScreen = childLists.get(whichScreen);

			nextScreen.left = currentScreen.getRight();
			nextScreen.layout();

			FlipperView tp = nextScreen;
			nextScreen = currentScreen;
			currentScreen = tp;

		}

		public final boolean isAtFront(int screenId) {
			int which = screenId - 1;
			if (which < 0) {
				which = getChildCount() - 1;
			}

			return screenId == childLists.get(which).id;
		}

		public final boolean isAtBack(int screenId) {
			int which = screenId + 1;
			if (which >= getChildCount()) {
				which = 0;
			}

			return screenId == childLists.get(which).id;
		}

		private final void changeMode(int screenId) {
			if (screenId == fScreen.id) {
				if (mMode == ModeHorizontal) {
					mMode = ModeVertical;
					bScreen.left = fScreen.left;
					bScreen.top = fScreen.getBottom();
				} else {
					mMode = ModeHorizontal;
					bScreen.left = fScreen.getRight();
					bScreen.top = fScreen.top;
				}

				bScreen.layout();
			} else {
				if (mMode == ModeHorizontal) {
					mMode = ModeVertical;
					fScreen.left = bScreen.left;
					fScreen.top = bScreen.top - mParentHeight;
				} else {
					mMode = ModeHorizontal;
					fScreen.left = bScreen.left - mParentWidth;
					fScreen.top = bScreen.top;
				}

				fScreen.layout();
			}
		}
	}
	
	private Handler handler = new Handler(Looper.getMainLooper()) {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
				case 0: {
					if (mScreenHelper != null && autoFlipping) {
						snapToScreen(mCurrentScreen);
					}
					break;
				}
				case 1: {
                    Log.e("ldx", "handler handleMessage..............");
                    postInvalidate();
					requestLayout();
					carousel();
					break;
				}
			}
		}
	};

	private void carousel() {
		if (autoFlippingThread.isAlive()) {
			return;
		}
		autoFlipping = false;
		if (!autoFlipping && mSize > 1) {
			autoFlipping = true;
			autoFlippingThread.start();
		}
	}

	@Override
	public void run() {
		int time = 10;
		while (mSize > 1) {
			while (time > 0) {
				try {
					Thread.sleep(SLEEP_TIME);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if (autoFlipping) {
					time--;
				}
			}

			handler.sendEmptyMessage(0);
			time = 10;
		}
	}

	public void setIndicator(IndicatorView indicator) {
		mIndicatorView = indicator;
	}

	private void addVelocityTracker(MotionEvent event) {
		if (mVelocityTracker == null) {
			mVelocityTracker = VelocityTracker.obtain();
		}
		mVelocityTracker.addMovement(event);
	}
	
	private void recycleTracker() {
		if (mVelocityTracker != null) {
			mVelocityTracker.recycle();
			mVelocityTracker = null;
		}
	}

	/** add flipper child */
	public <T extends BaseObject> void setFlipperView(List<T> banners) {
        if (getChildCount() > 0) {
            removeAllViews();
        }
		mSize = banners.size();
		isLayout = false;
		for (int i = 0; i < banners.size(); ++i) {
			final ImageView child = new ImageView(getContext());
			child.setScaleType(ImageView.ScaleType.FIT_XY);
			addView(child, i);
			T t = banners.get(i);
			loadBanners(t, child);
		}
		handler.sendEmptyMessage(1);
	}

	/** base url download bitmap **/
	private void loadBanners(BaseObject banner, final ImageView child) {
		if (banner != null) {
			ImageLoaderHelper.getInstance().loadImage(banner.getBannerOrImgURL(), child, new ImageLoadingListener() {
				@Override
				public void onLoadingStarted(String imageUri, View view) {

				}

				@Override
				public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

				}

				@Override
				public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
					if (loadedImage != null) {
						child.setImageBitmap(loadedImage);
					}
				}

				@Override
				public void onLoadingCancelled(String imageUri, View view) {

				}
			});
		}
	}

	private void isAllowOnInterceptTouchEvent(boolean isAllow) {
		if (getParent() != null) {
			getParent().requestDisallowInterceptTouchEvent(isAllow);
		}
	}

    public void setClickListener(IClickListener listener) {
        mClickListener = listener;
    }

    public interface IClickListener {
        void onClick(int position);
    }
}