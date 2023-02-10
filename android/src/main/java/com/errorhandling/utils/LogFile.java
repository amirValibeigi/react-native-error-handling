package com.errorhandling.utils;

import android.content.Context;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class LogFile {
  private final Context pContext;
  private String nameLog = "";

  public LogFile(Context context) {
    pContext = context;
    nameLog = "log_" + (new SimpleDateFormat("yyyy_MM_dd", Locale.US).format(new Date())) + ".txt";


    makeDirAndClean(context);
  }

  public LogFile log(String key, String name, String message) {
    try {
      File file = lastLogFile();

      FileOutputStream fos = new FileOutputStream(file, true);
      fos.write((System.currentTimeMillis() + "_" + key + " " + name + ": " + message + "\n").getBytes());
      fos.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return this;
  }

  public LogFile logE(String name, String message) {
    return log("E", name, message);
  }

  public LogFile logS(String name, String message) {
    return log("S", name, message);
  }

  public LogFile logV(String name, String message) {
    return log("V", name, message);
  }

  public File lastLogFile(){
    return new File(pContext.getFilesDir(), "logs/" + nameLog);
  }

  private void makeDirAndClean(Context context) {
    try {
      File folder = new File(context.getFilesDir(), "logs");

      if (!folder.exists()) {
        folder.mkdirs();
      }

      File[] files = folder.listFiles();

      if (files != null && files.length > 0) {
        for (File file : files) {
          if (!file.getName().equals(nameLog)) {
            file.delete();
          }
        }
      }
    } catch (Throwable e) {
      e.printStackTrace();
    }

  }

}
