/**
 * @file XFooterView.java
 * @create Mar 31, 2012 9:33:43 PM
 * @author Maxwin
 * @description XListView's footer
 */
package com.yinuo.ui.component.widget.view;


import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yinuo.R;


public class ListViewFooterView extends LinearLayout {
	private Context mContext;

	private View mContentView;
	private View mProgressBar;
	private TextView mHintView;
	private TextView txt_progresstext;
	private LinearLayout layout_progress;
	public enum STATE{
		normal,
		ready,
		loading
	}
	public ListViewFooterView(Context context) {
		super(context);
		initView(context);
	}
	
	public ListViewFooterView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}

	
	public void setState(STATE state) {
		mHintView.setVisibility(View.INVISIBLE);
		mProgressBar.setVisibility(View.INVISIBLE);
		mHintView.setVisibility(View.INVISIBLE);
         txt_progresstext.setVisibility(View.INVISIBLE);
         layout_progress.setVisibility(View.INVISIBLE);
		if (state == STATE.ready) {
			mHintView.setVisibility(View.VISIBLE);
			mHintView.setText(getResources().getString(R.string.app_refresh_listfooterview_ready));
		} else if (state == STATE.loading) {
			mProgressBar.setVisibility(View.VISIBLE);
             layout_progress.setVisibility(View.VISIBLE);
             txt_progresstext.setVisibility(View.VISIBLE);
		} else {
			mHintView.setVisibility(View.VISIBLE);
			mHintView.setText(getResources().getString(R.string.app_refresh_listfooterview_normal));
		}
	}
	
	public void setBottomMargin(int height) {
		if (height < 0) return ;
		LayoutParams lp = (LayoutParams)mContentView.getLayoutParams();
		lp.bottomMargin = height;
		mContentView.setLayoutParams(lp);
	}
	
	public int getBottomMargin() {
		LayoutParams lp = (LayoutParams)mContentView.getLayoutParams();
		return lp.bottomMargin;
	}
	
	/**
	 * normal status
	 */
	public void normal() {
		mHintView.setVisibility(View.VISIBLE);
		mProgressBar.setVisibility(View.GONE);
         layout_progress.setVisibility(View.GONE);
         txt_progresstext.setVisibility(View.GONE);
	}
	
	
	/**
	 * loading status 
	 */
	public void loading() {
		mHintView.setVisibility(View.GONE);
		mProgressBar.setVisibility(View.VISIBLE);
        layout_progress.setVisibility(View.VISIBLE);
         txt_progresstext.setVisibility(View.VISIBLE);
	}
	
	/**
	 * hide footer when disable pull load more
	 */
	public void hide() {
		LayoutParams lp = (LayoutParams)mContentView.getLayoutParams();
		lp.height = 0;
		mContentView.setLayoutParams(lp);
	}
	
	/**
	 * show footer
	 */
	public void show() {
		LayoutParams lp = (LayoutParams)mContentView.getLayoutParams();
		lp.height = LayoutParams.WRAP_CONTENT;
		mContentView.setLayoutParams(lp);
	}
	
	private void initView(Context context) {
		mContext = context;
		LinearLayout moreView = (LinearLayout) LayoutInflater.from(mContext).inflate(R.layout.app_refresh_listview_footer, null);
		addView(moreView);
		moreView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		
		mContentView = moreView.findViewById(R.id.waterdroplistview_footer_content);
		mProgressBar = moreView.findViewById(R.id.app_listview_footer_progress);
		mHintView = (TextView)moreView.findViewById(R.id.app_refresh_listview_footer_hint_textview);
         layout_progress=(LinearLayout)moreView.findViewById(R.id.progresslayout);
         txt_progresstext =(TextView)moreView.findViewById(R.id.app_refresh_loading_progress_notify);
	}
	
	
}
