package com.yinuo.ui.component.widget.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

import com.yinuo.adapter.CityChooseGridViewAdapter;
import com.yinuo.mode.AddressModel;

import java.util.List;

public class CityChooseRecentGridView extends GridView {
	private CityChooseGridViewAdapter mAdapter;

	public CityChooseRecentGridView(Context context) {
		this(context, null);
	}

	public CityChooseRecentGridView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public CityChooseRecentGridView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		mAdapter = new CityChooseGridViewAdapter(context);
	}

	public void setList(List<AddressModel> list) {
		mAdapter.setList(list);
		setAdapter(mAdapter);
	}

	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}

	public CityChooseGridViewAdapter getGridViewAdapter() {
		return mAdapter;
	}
}