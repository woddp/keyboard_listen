package com.woddp.keyboard_listen;


import io.flutter.plugin.common.EventChannel;
import io.flutter.plugin.common.EventChannel.EventSink;
import io.flutter.plugin.common.EventChannel.StreamHandler;
import io.flutter.plugin.common.PluginRegistry.Registrar;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;


/** KeyboardListenPlugin */
public class KeyboardListenPlugin implements StreamHandler,Application.ActivityLifecycleCallbacks, ViewTreeObserver.OnGlobalLayoutListener{


  private static final String STREAM_CHANNEL_NAME = "keyboard_listen_plugin";
  EventSink eventsSink;
  boolean isVisible;
  View mainView;

  Activity activity;
  Context context;


  Map<String,Integer> res= new HashMap<>();
  KeyboardListenPlugin(Registrar registrar) {
    eventsSink = null;
    mainView = ((ViewGroup) registrar.activity().findViewById(android.R.id.content)).getChildAt(0);
    mainView.getViewTreeObserver().addOnGlobalLayoutListener(this);

    this.activity=registrar.activity();
    this.context=registrar.context();

    res.put("height",0);
    res.put("isVisible",isVisible ? 1 : 0);
  }

  @Override
  public void onGlobalLayout() {
    Rect r = new Rect();

    mainView.getWindowVisibleDisplayFrame(r);

    // check if the visible part of the screen is less than 85%
    // if it is then the keyboard is showing
    boolean newState = ((double)r.height() / (double)mainView.getRootView().getHeight()) < 0.85;

    if (newState != isVisible) {
      isVisible = newState;
      if (eventsSink != null) {
        int height=getBottomStatusHeight(this.context);
        res.put("height",height);
        res.put("isVisible",isVisible ? 1 : 0);
        eventsSink.success(res);
      }
    }
  }
////////////////////////////////////////////////////////////////////////

  public static int getBottomStatusHeight(Context context) {
    int totalHeight = getDpi(context);

    int contentHeight = getScreenHeight(context);

    return totalHeight - contentHeight;
  }



  public static int getDpi(Context context) {
    int dpi = 0;
    WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    Display display = windowManager.getDefaultDisplay();
    DisplayMetrics displayMetrics = new DisplayMetrics();
    @SuppressWarnings("rawtypes")
    Class c;
    try {
      c = Class.forName("android.view.Display");
      @SuppressWarnings("unchecked")
      Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
      method.invoke(display, displayMetrics);
      dpi = displayMetrics.heightPixels;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return dpi;
  }

  public static int getScreenHeight(Context context) {
    WindowManager wm = (WindowManager) context
            .getSystemService(Context.WINDOW_SERVICE);
    DisplayMetrics outMetrics = new DisplayMetrics();
    wm.getDefaultDisplay().getMetrics(outMetrics);
    return outMetrics.heightPixels;
  }




  ////////////////////////////////////////////////////////////////////




  @Override
  public void onActivityCreated(Activity activity, Bundle bundle) {

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
  public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

  }

  @Override
  public void onActivityDestroyed(Activity activity) {

    mainView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
  }

  public static void registerWith(Registrar registrar) {

    final EventChannel eventChannel = new EventChannel(registrar.messenger(), STREAM_CHANNEL_NAME);
    KeyboardListenPlugin instance = new KeyboardListenPlugin(registrar);
    eventChannel.setStreamHandler(instance);
  }





  @Override
  public void onListen(Object arguments, final EventSink eventsSink) {
    // register listener
    this.eventsSink = eventsSink;

    // is keyboard is visible at startup, let our subscriber know
    if (isVisible) {
      eventsSink.success(res);
    }
  }

  @Override
  public void onCancel(Object arguments) {
    eventsSink = null;
  }

}
