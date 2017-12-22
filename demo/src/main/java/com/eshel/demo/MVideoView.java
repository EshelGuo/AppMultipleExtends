package com.eshel.demo;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.VideoView;

/**
 * Created by guoshiwen on 2017/12/20.
 */

public class MVideoView extends VideoView{
	public int mVideoWidth;
	public int mVideoHeight;
	public MVideoView(Context context) {
		super(context);
	}

	public MVideoView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int width = getDefaultSize(mVideoWidth,widthMeasureSpec);
		int height = getDefaultSize(mVideoHeight,heightMeasureSpec);
		setMeasuredDimension(width,height);
//		super.onMeasure(widthMeasureSpec,heightMeasureSpec);
	}
}
