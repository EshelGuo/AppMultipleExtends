package com.eshel.demo;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.widget.Toast;

/**
 * Created by guoshiwen on 2017/12/20.
 */

public class SDK2Application extends Application{
	@Override
	public void onCreate() {
		super.onCreate();
		MainActivity.log("SDK2"+this.getApplicationContext().toString());
		MainActivity.log("SDK2"+this.toString());
		MainApplication.mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				Toast.makeText(SDK2Application.this,"sdk2 toast", Toast.LENGTH_LONG).show();
			}
		},6000);
		MainApplication.mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				Toast.makeText(getApplicationContext(),"sdk2 toast2",Toast.LENGTH_LONG).show();
			}
		},8000);
		registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
			@Override
			public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
				MainActivity.log("SDK2"+activity.toString());
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
