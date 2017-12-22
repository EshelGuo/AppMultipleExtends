package com.eshel.demo.hookapp;

import android.app.Application;

/**
 * Created by guoshiwen on 2017/12/22.
 */

public class AppConfig {

	public void add(Application application) {
		AppManager.add(application);
	}
}
