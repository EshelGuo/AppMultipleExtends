package com.eshel.demo;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Handler;
import android.widget.Toast;

import com.eshel.demo.hookapp.AppConfig;
import com.eshel.demo.hookapp.AppManager;
import com.eshel.demo.hookapp.HookApplication;


/**
 * Created by guoshiwen on 2017/12/20.
 */

public class MainApplication extends HookApplication {
	public static Handler mHandler = new Handler();

	@Override
	public void addApplications(AppConfig appConfig) {
		appConfig.add(new SDK1Application());
		appConfig.add(new SDK2Application());
	}

	@Override
	public void onCreate() {
		super.onCreate();
		Toast.makeText(getApplicationContext(), "MainApp toast", Toast.LENGTH_SHORT).show();
	}
}
