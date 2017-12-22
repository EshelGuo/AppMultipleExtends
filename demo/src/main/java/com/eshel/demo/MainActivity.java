package com.eshel.demo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.media.MediaMetadata;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

	private static final int SELECTOR_VIDEO = 1;
	private String mPath;
	private MVideoView mVideoview;
	private Button mSelect;
	private TextView mShow;
	Handler mHandler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mVideoview = (MVideoView) findViewById(R.id.videoview);
		mSelect = (Button) findViewById(R.id.select);
		mShow = (TextView) findViewById(R.id.show);
		mSelect.setOnClickListener(this);
		new Thread(new Runnable() {
			@Override
			public void run() {
				mHandler = new Handler(getMainLooper());
				mHandler.postDelayed(new Runnable() {
					@Override
					public void run() {
						mSelect.setText("success");
					}
				},1000);
			}
		}).start();
		Log.i("context act",getImpl(this));
		Log.i("context act",getImpl(getWindow().getContext()));
		Log.i("context act",getImpl(mShow.getContext()));
		Log.i("context act",getImpl(mVideoview.getContext()));
		Log.i("context act",getImpl(mSelect.getContext()));
		Log.i("context app",getImpl(getApplication()));
	}

	static String getImpl(Context context) {
		Context nextContext;
		while ((context instanceof ContextWrapper) &&
				(nextContext=((ContextWrapper)context).getBaseContext()) != null) {
			context = nextContext;
		}
		return context.toString();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (resultCode == Activity.RESULT_OK) {
			// Get the Uri of the selected file
			Uri uri = data.getData();
			Log.i(MainActivity.class.getSimpleName(),uri.toString());
			try {
				File file = new File(uri.getPath());
				mPath = file.getPath();
				mShow.setText(mPath);
				mSelect.postDelayed(new Runnable() {
					@Override
					public void run() {
						playVideo();
					}
				},2000);
				Log.i(MainActivity.class.getSimpleName(), "mPath: " + mPath);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	private void playVideo() {
		Log.i("SDK",Environment.getExternalStorageDirectory().getAbsolutePath());
		mPath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/super_screenshot/screen_record/20171220_151645.mp4";
		MediaMetadataRetriever retr = new MediaMetadataRetriever();
		retr.setDataSource(mPath);
		int width = Integer.parseInt(retr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH));
		int height = Integer.parseInt(retr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT));
		float scale = width / height;
		int screenWidth = getResources().getDisplayMetrics().widthPixels;
		int screenHeight = getResources().getDisplayMetrics().heightPixels;
		if(width < screenWidth){
			width = screenWidth;
			height = (int) (width / scale);
		}
		if(height < screenHeight){
			height = screenHeight;
			width = (int) (scale * height);
		}
		retr.release();
//		mVideoview.mVideoWidth = width;
//		mVideoview.mVideoHeight = height;
//		mVideoview.measure(0,0);
//		mVideoview.invalidate();
		Uri uri = Uri.parse(mPath);
		mVideoview.setVisibility(View.VISIBLE);
		mVideoview.setVideoURI(uri);
		mVideoview.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
			@Override
			public void onPrepared(MediaPlayer mp) {
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
					mp.setVideoScalingMode(MediaPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING);
				}
			}
		});
		mVideoview.start();
	}

	@Override
	public void onClick(View v) {
		showFileChooser();
	}
	/** 调用文件选择软件来选择文件 **/
	private void showFileChooser() {
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		//intent.setType(“image/*”);//选择图片
		//intent.setType(“audio/*”); //选择音频
		intent.setType("video/*"); //选择视频 （mp4 3gp 是android支持的视频格式）
		//intent.setType(“video/*;image/*”);//同时选择视频和图片
//		 intent.setType("*/*");//无类型限制
		intent.addCategory(Intent.CATEGORY_OPENABLE);
		try {
			startActivityForResult(Intent.createChooser(intent, "请选择一个要上传的文件"),
					SELECTOR_VIDEO);
		} catch (android.content.ActivityNotFoundException ex) {
			// Potentially direct the user to the Market with a Dialog
			Toast.makeText(this, "请安装文件管理器", Toast.LENGTH_SHORT)
					.show();
		}
	}
	public static void log(String msg){
		Log.i("MainActivity",msg);
	}
}
