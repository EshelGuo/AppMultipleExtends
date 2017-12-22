package com.eshel.demo;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.widget.Toast;


/**
 * Created by guoshiwen on 2017/12/20.
 */

public class SDK1Application extends Application{

	@Override
	public void onCreate() {
		super.onCreate();
		initSDK1();
	}

	private void initSDK1() {
		MainActivity.log("SDK1"+this.getApplicationContext().toString());
		MainActivity.log("SDK1"+this.toString());
		MainApplication.mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				Toast.makeText(SDK1Application.this,"sdk1 toast", Toast.LENGTH_LONG).show();
			}
		},2000);
		MainApplication.mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				Toast.makeText(getApplicationContext(),"sdk1 toast2",Toast.LENGTH_LONG).show();
			}
		},4000);
		registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
			@Override
			public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
				MainActivity.log("SDK1"+activity.toString());
			}

			@Override
			public void onActivityStarted(Activity activity) {

			}

			@Override
			public void onActivityResumed(Activity activity) {

			}

			@Override
			public void onActivityPaused(Activity activity) {

			}

			@Override
			public void onActivityStopped(Activity activity) {

			}

			@Override
			public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

			}

			@Override
			public void onActivityDestroyed(Activity activity) {

			}
		});
	}
}
