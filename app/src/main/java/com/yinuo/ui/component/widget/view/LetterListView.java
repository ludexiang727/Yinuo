package com.yinuo.ui.component.widget.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.yinuo.R;

public class LetterListView extends View {

	private OnTouchingLetterChangedListener onTouchingLetterChangedListener;
	private String[] mStrArray = getContext().getResources().getStringArray(R.array.city_choose_page_letter);
	private String[] mLetter = {mStrArray[0], mStrArray[1], mStrArray[2], mStrArray[3], "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K",
			"L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X",
			"Y", "Z" };
	private int mChoose = -1;
	private Paint mPaint = new Paint();
	private boolean mShowLetterBg = false;

	public LetterListView(Context context) {
		this(context, null);
	}

	public LetterListView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public LetterListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (mShowLetterBg) {
			canvas.drawColor(Color.parseColor("#40000000"));
		}
		int height = getHeight();
		int width = getWidth();
		int singleHeight = height / mLetter.length;
		for (int i = 0; i < mLetter.length; i++) {
			mPaint.setColor(Color.parseColor("#8c8c8c"));
			mPaint.setTextSize(26);
			// mPaint.setTypeface(Typeface.DEFAULT_BOLD);
			mPaint.setAntiAlias(true);
			/*if (i == mChoose) {
				mPaint.setColor(Color.parseColor("#3399ff"));
				mPaint.setFakeBoldText(true);
			}*/
			float xPos = width / 2 - mPaint.measureText(mLetter[i]) / 2;
			float yPos = singleHeight * i + singleHeight;
			canvas.drawText(mLetter[i], xPos, yPos, mPaint);
			mPaint.reset();
		}
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		final int action = event.getAction();
		final float y = event.getY();
		final int oldChoose = mChoose;
		final OnTouchingLetterChangedListener listener = onTouchingLetterChangedListener;
		final int c = (int) (y / getHeight() * mLetter.length);
		switch (action) {
			case MotionEvent.ACTION_DOWN: {
				mShowLetterBg = true;
				if (oldChoose != c && listener != null) {
					if (c >= 0 && c < mLetter.length) {
						listener.onTouchingLetterChanged(mLetter[c]);
						mChoose = c;
						invalidate();
					}
				}
				break;
			}
			case MotionEvent.ACTION_MOVE: {
				if (oldChoose != c && listener != null) {
					if (c >= 0 && c < mLetter.length) {
						listener.onTouchingLetterChanged(mLetter[c]);
						mChoose = c;
						invalidate();
					}
				}
				break;
			}
			case MotionEvent.ACTION_UP: {
				mShowLetterBg = false;
				mChoose = -1;
				invalidate();
				break;
			}
		}
		return true;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return super.onTouchEvent(event);
	}

	public void setOnTouchingLetterChangedListener(OnTouchingLetterChangedListener touchListener) {
		this.onTouchingLetterChangedListener = touchListener;
	}

	public interface OnTouchingLetterChangedListener {
		void onTouchingLetterChanged(String s);
	}
}
