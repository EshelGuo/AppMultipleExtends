package com.eshel.demo.hookapp;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import java.util.ArrayList;

/**
 * Created by guoshiwen on 2017/12/22
 */

class ApplicationCache {
	static final int ON_ACTIVITY_CREATED = 0;
	static final int ON_ACTIVITY_STARTED= 1;
	static final int ON_ACTIVITY_RESUMED = 2;
	static final int ON_ACTIVITY_PAUSED= 3;
	static final int ON_ACTIVITY_STOPPED= 4;
	static final int ON_ACTIVITY_SAVEINSTANCESTATE= 5;
	static final int ON_ACTIVITY_DESTROYED= 6;
	Application app;
	private ArrayList<Application.ActivityLifecycleCallbacks> mActivityLifecycleCallbackCache =
			new ArrayList<>();
	ApplicationCache(Application app) {
		this.app = app;
		mActivityLifecycleCallbackCache = (ArrayList<Application.ActivityLifecycleCallbacks>) ReflectUtil.getMember(Application.class,app,"mActivityLifecycleCallbacks");
	}
	void dispatchActivityLifecycleCallback(int type, Activity activity, Bundle data){
		if(mActivityLifecycleCallbackCache != null) {
			for (Application.ActivityLifecycleCallbacks activityLifecycleCallback : mActivityLifecycleCallbackCache) {
				switch (type) {
					case ON_ACTIVITY_CREATED:
						activityLifecycleCallback.onActivityCreated(activity,data);
						break;
					case ON_ACTIVITY_STARTED:
						activityLifecycleCallback.onActivityStarted(activity);
						break;
					case ON_ACTIVITY_RESUMED:
						activityLifecycleCallback.onActivityResumed(activity);
						break;
					case ON_ACTIVITY_PAUSED:
						activityLifecycleCallback.onActivityPaused(activity);
						break;
					case ON_ACTIVITY_STOPPED:
						activityLifecycleCallback.onActivityStopped(activity);
						break;
					case ON_ACTIVITY_SAVEINSTANCESTATE:
						activityLifecycleCallback.onActivitySaveInstanceState(activity,data);
						break;
					case ON_ACTIVITY_DESTROYED:
						activityLifecycleCallback.onActivityDestroyed(activity);
						break;
				}
			}
		}
	}

}
