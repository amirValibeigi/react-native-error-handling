package com.errorhandling;

import androidx.annotation.NonNull;

import com.errorhandling.utils.LogFile;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.module.annotations.ReactModule;

@ReactModule(name = ErrorHandlingModule.NAME)
public class ErrorHandlingModule extends ReactContextBaseJavaModule {
  public static final String NAME = "ErrorHandling";
  private final LogFile logFile;

  public ErrorHandlingModule(ReactApplicationContext reactContext) {
    super(reactContext);
    logFile = new LogFile(reactContext);
  }

  @Override
  @NonNull
  public String getName() {
    return NAME;
  }

  @ReactMethod()
  public void log(String key, String name, String message) {
    try {
      logFile.log(key, name, message);
    } catch (Throwable e) {
      e.printStackTrace();
    }
  }

  @ReactMethod()
  public void logV(String name, String message) {
    log("V", name, message);
  }

  @ReactMethod()
  public void logE(String name, String message) {
    log("E", name, message);
  }

  @ReactMethod()
  public void logS(String name, String message) {
    log("S", name, message);
  }

  @ReactMethod()
  public void testError() {
    int i = 1 / 0;
  }
}
