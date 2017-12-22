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
	private static List<Application> apps = new ArrayList<>();
	static Application mainApp;
	static void init(Application application){
		mainApp = application;
	}
	static void add(Application app){
		ReflectUtil.setMember(Application.class,app,"mComponentCallbacks",new HookArrayList(mainApp));
		ReflectUtil.setMember(Application.class,app,"mActivityLifecycleCallbacks",new HookArrayList(mainApp));
		ReflectUtil.setMember(Application.class,app,"mAssistCallbacks",new HookArrayList(mainApp));
		apps.add(app);
	}
	public static void attachBaseContext(){
		dispatchApplication(attachBaseContext,mainApp,null,0);
	}

	public static void onCreate(){
		dispatchApplication(onCreate,null,null,0);
	}
	public static void onTerminate(){
		dispatchApplication(onTerminate,null,null,0);
	}

	public static void onConfigurationChanged(Configuration newConfig) {
		dispatchApplication(onConfigurationChanged,null,newConfig,0);
	}

	public static void onTrimMemory(int level) {
		dispatchApplication(onTrimMemory,null,null,level);
	}

	public static void onLowMemory() {
		dispatchApplication(onTrimMemory,null,null,0);
	}

	static abstract class Runnable{
		abstract void run(Application app);
		public final void start(){
			for (Application app : apps) {
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
	private static void dispatchApplication(int type,Application app1,Configuration newConfig,int level){
		if(apps != null)
			for (Application app : apps) {
				try {
				    switch (type){
						case attachBaseContext:
							ReflectUtil.invoke(android.content.ContextWrapper.class,app,"attachBaseContext",
									new Class[]{Context.class},new Object[]{app1.getBaseContext()});
							break;
						case onCreate:
							app.onCreate();
							break;
						case onTerminate:
							app.onTerminate();
							break;
						case onConfigurationChanged:
							app.onConfigurationChanged(newConfig);
							break;
						case onTrimMemory:
							app.onTrimMemory(level);
							break;
						case onLowMemory:
							app.onLowMemory();
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
