package com.errorhandling.utils;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CallAPI extends AsyncTask<Map<String, String>, Void, Void> {

  @Override
  protected void onPreExecute() {
    super.onPreExecute();
  }

  @Override
  protected Void doInBackground(Map<String, String>... params) {
    try {
      Map<String, String> map = params[0];
      String urlString = map.get("url");
      StringBuilder data = new StringBuilder();

      map.remove("url");
      OkHttpClient client = new OkHttpClient().newBuilder().build();
      MultipartBody.Builder body = new MultipartBody.Builder().setType(MultipartBody.FORM);

      for (String k : map.keySet()) {
        if (k.equals("file")) {
          body.addFormDataPart("file", map.get(k), RequestBody.create(MediaType.parse("application/octet-stream"), new File(map.get(k))));
        } else {
          body.addFormDataPart(k, Objects.requireNonNull(map.get(k)));
        }
      }


      Request request = new Request.Builder().url(urlString).method("POST", body.build()).build();
      Response response = client.newCall(request).execute();

      if (response.code() == 200) {
        Log.i("response", "doInBackground: Success");
      } else {
        Log.i("response", "doInBackground: Error");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    return null;
  }
}
