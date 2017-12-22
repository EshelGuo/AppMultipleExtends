package com.eshel.demo.hookapp;

import android.app.Application;
import android.content.ComponentCallbacks;
import android.os.Build;

import java.util.ArrayList;

/**
 * Created by guoshiwen on 2017/12/22
 */

public class HookArrayList<E> extends ArrayList<E>{
	private static Application mApplication;
	public HookArrayList(Application application){
		mApplication = application;
	}
	@Override
	public boolean add(E o) {
		if(o instanceof Application.ActivityLifecycleCallbacks){
			mApplication.registerActivityLifecycleCallbacks((Application.ActivityLifecycleCallbacks) o);
		}else if(o instanceof ComponentCallbacks){
			mApplication.registerComponentCallbacks((ComponentCallbacks) o);
		}else if(o instanceof Application.OnProvideAssistDataListener){
			if (Build.VERSION.SDK_INT >= 18/*Build.VERSION_CODES.JELLY_BEAN_MR2*/) {
				mApplication.registerOnProvideAssistDataListener((Application.OnProvideAssistDataListener) o);
			}
		}
		return true;
	}
	public boolean remove(Object o) {
		if(o instanceof Application.ActivityLifecycleCallbacks){
			mApplication.unregisterActivityLifecycleCallbacks((Application.ActivityLifecycleCallbacks) o);
		}else if(o instanceof ComponentCallbacks){
			mApplication.unregisterComponentCallbacks((ComponentCallbacks) o);
		}else if(o instanceof Application.OnProvideAssistDataListener){
			if (Build.VERSION.SDK_INT >= 18/*Build.VERSION_CODES.JELLY_BEAN_MR2*/) {
				mApplication.unregisterOnProvideAssistDataListener((Application.OnProvideAssistDataListener) o);
			}
		}
		return true;
	}
}
