package com.errorhandling.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Looper;

import androidx.annotation.NonNull;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

import com.errorhandling.CrashActivity;
import android.content.pm.PackageManager;
import android.content.pm.PackageInfo;

/**
 * report exception to api
 */
public class ExceptionHandler implements Thread.UncaughtExceptionHandler {

  private Context context = null;
  private boolean groupDebug = false;
  private String callBackActivity = null;
  private String urlApi = null;


  /**
   * constructor Exception Handler
   *
   * @param context context for run activity
   * @param urlApi  url api
   */
  public ExceptionHandler(Context context, String urlApi) {
    this.context = context;
    this.urlApi = urlApi;
  }

  /**
   * constructor Exception Handler
   *
   * @param context          context for run activity
   * @param urlApi  url api
   * @param callBackActivity run activity after show crash activity
   */
  public ExceptionHandler(Context context, String urlApi, Class<?> callBackActivity) {
    this.context = context;
    this.urlApi = urlApi;
    this.callBackActivity = callBackActivity.getName();
  }

  /**
   * constructor Exception Handler
   *
   * @param context          context for run activity
   * @param urlApi  url api
   * @param callBackActivity run activity after show crash activity
   * @param groupDebug       share exception with social media
   */
  public ExceptionHandler(Context context, String urlApi, Class<?> callBackActivity, boolean groupDebug) {
    this.context = context;
    this.urlApi = urlApi;
    this.callBackActivity = callBackActivity.getName();
    this.groupDebug = groupDebug;
  }

  @Override
  public void uncaughtException(@NonNull Thread t, @NonNull Throwable e) {
    Writer writer = new StringWriter();
    e.printStackTrace(new PrintWriter(writer));

    startCrashActivity(writer.toString());
  }

  private void startCrashActivity(String trace) {
    new Thread(() -> {
      String versionName = "-";
      String versionCode = "-";
      String packageName = "-";
      
      try {
        PackageInfo info = context.getPackageManager()
        .getPackageInfo(context.getPackageName(), 0);
        versionName = info.versionName;
        packageName = info.packageName;
        versionCode = String.valueOf(info.versionCode);

      } catch (PackageManager.NameNotFoundException e) {
      }

      Looper.prepare();

      Intent i = new Intent(context, CrashActivity.class);
      i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
      i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
      i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

      i.putExtra(CrashActivity.KeyInfo, "p: " + Build.PRODUCT + "\n" + "d: " + Build.DEVICE + "\n" + "s: " + Build.VERSION.SDK_INT  + "\n" + "vc: " + versionCode + "\n" + "pn: " + packageName + "\n" + "vn: " + versionName);
      i.putExtra(CrashActivity.KeyTrace, trace);
      i.putExtra(CrashActivity.KeyUrlApi, urlApi);
      i.putExtra(CrashActivity.KeyDebugGroups, groupDebug);
      i.putExtra(CrashActivity.KeyCallBackActivity, callBackActivity);

      context.startActivity(i);
      android.os.Process.killProcess(android.os.Process.myPid());
    }).start();
  }

}
