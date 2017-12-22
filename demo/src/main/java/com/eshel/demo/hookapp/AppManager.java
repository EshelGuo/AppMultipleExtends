package com.eshel.demo.hookapp;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by guoshiwen on 2017/12/20
 */

public class AppManager {
	private static final int attachBaseContext = 0;
	private static final int onCreate = 1;
	private static final int onTerminate = 2;
	private static final int onConfigurationChanged = 3;
	private static final int onTrimMemory = 4;
	private static final int onLowMemory = 5;
	private static List<ApplicationCache> apps = new ArrayList<>();
	static void add(Application app){
		apps.add(new ApplicationCache(app));
	}
	public static void attachBaseContext(final Application application){
		hookActivityLifeListener(application);
		dispatchApplicationCache(attachBaseContext,application,null,0);
	}
	private static void hookActivityLifeListener(Application application) {
		application.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
			@Override
			public void onActivityCreated(final Activity activity, final Bundle savedInstanceState) {
				new Runnable(){
					@Override
					void run(ApplicationCache app) {
						app.dispatchActivityLifecycleCallback(ApplicationCache.ON_ACTIVITY_CREATED,activity,savedInstanceState);
					}
				}.start();
			}

			@Override
			public void onActivityStarted(final Activity activity) {
				new Runnable(){
					@Override
					void run(ApplicationCache app) {
						app.dispatchActivityLifecycleCallback(ApplicationCache.ON_ACTIVITY_STARTED,activity,null);
					}
				}.start();
			}

			@Override
			public void onActivityResumed(final Activity activity) {
				new Runnable(){
					@Override
					void run(ApplicationCache app) {
						app.dispatchActivityLifecycleCallback(ApplicationCache.ON_ACTIVITY_RESUMED,activity,null);
					}
				}.start();
			}

			@Override
			public void onActivityPaused(final Activity activity) {
				new Runnable(){
					@Override
					void run(ApplicationCache app) {
						app.dispatchActivityLifecycleCallback(ApplicationCache.ON_ACTIVITY_PAUSED,activity,null);
					}
				}.start();
			}

			@Override
			public void onActivityStopped(final Activity activity) {
				new Runnable(){
					@Override
					void run(ApplicationCache app) {
						app.dispatchActivityLifecycleCallback(ApplicationCache.ON_ACTIVITY_STOPPED,activity,null);
					}
				}.start();
			}

			@Override
			public void onActivitySaveInstanceState(final Activity activity, final Bundle outState) {
				new Runnable(){
					@Override
					void run(ApplicationCache app) {
						app.dispatchActivityLifecycleCallback(ApplicationCache.ON_ACTIVITY_SAVEINSTANCESTATE,activity,outState);
					}
				}.start();
			}

			@Override
			public void onActivityDestroyed(final Activity activity) {
				new Runnable(){
					@Override
					void run(ApplicationCache app) {
						app.dispatchActivityLifecycleCallback(ApplicationCache.ON_ACTIVITY_DESTROYED,activity,null);
					}
				}.start();
			}
		});
	}

	public static void onCreate(){
		dispatchApplicationCache(onCreate,null,null,0);
	}
	public static void onTerminate(){
		dispatchApplicationCache(onTerminate,null,null,0);
	}

	public static void onConfigurationChanged(Configuration newConfig) {
		dispatchApplicationCache(onConfigurationChanged,null,newConfig,0);
	}

	public static void onTrimMemory(int level) {
		dispatchApplicationCache(onTrimMemory,null,null,level);
	}

	public static void onLowMemory() {
		dispatchApplicationCache(onTrimMemory,null,null,0);
	}

	static abstract class Runnable{
		abstract void run(ApplicationCache app);
		public final void start(){
			for (ApplicationCache app : apps) {
				try {
					run(app);
				}catch (Exception e){
					e.printStackTrace();
				}catch (Error e){
					e.printStackTrace();
				}
			}
		}
	}
	private static void dispatchApplicationCache(int type,Application app1,Configuration newConfig,int level){
		if(apps != null)
			for (ApplicationCache app : apps) {
				try {
				    switch (type){
						case attachBaseContext:
							ReflectUtil.invoke(android.content.ContextWrapper.class,app.app,"attachBaseContext",
									new Class[]{Context.class},new Object[]{app1.getBaseContext()});
							break;
						case onCreate:
							app.app.onCreate();
							break;
						case onTerminate:
							app.app.onTerminate();
							break;
						case onConfigurationChanged:
							app.app.onConfigurationChanged(newConfig);
							break;
						case onTrimMemory:
							app.app.onTrimMemory(level);
							break;
						case onLowMemory:
							app.app.onLowMemory();
							break;
					}
				}catch (Exception e){
				    e.printStackTrace();
				}catch (Error e){
				    e.printStackTrace();
				}
			}
	}
}
