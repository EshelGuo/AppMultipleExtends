package com.eshel.demo.hookapp;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;

/**
 * Created by guoshiwen on 2017/12/22.
 */

public abstract class HookApplication extends Application{

	public abstract void addApplications(AppConfig appConfig);
	@Override
	protected void attachBaseContext(Context base) {
		super.attachBaseContext(base);
		addApplications(new AppConfig());
		AppManager.attachBaseContext(this);
	}

	@Override
	public void onCreate() {
		super.onCreate();
		AppManager.onCreate();
	}

	@Override
	public void onTerminate() {
		super.onTerminate();
		AppManager.onTerminate();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		AppManager.onConfigurationChanged(newConfig);
	}

	@Override
	public void onLowMemory() {
		super.onLowMemory();
		AppManager.onLowMemory();
	}

	@Override
	public void onTrimMemory(int level) {
		super.onTrimMemory(level);
		AppManager.onTrimMemory(level);
	}
}
