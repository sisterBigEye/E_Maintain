package com.yy.electric.maintenance.api;

import android.graphics.Bitmap;
import android.util.Base64;

import com.yy.electric.maintenance.base.IRequest;
import com.yy.electric.maintenance.feature.BitmapInfo;
import com.yy.electric.maintenance.task.BitmapTask;
import com.yy.electric.maintenance.util.BitmapUtil;
import com.yy.electric.maintenance.util.JsonUtil;
import com.yy.electric.maintenance.util.LogUtil;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class HttpApi {

  private static final String TAG = "HttpApi";

  private static String mUserName;

  private static final MediaType TYPE_JSON = MediaType.parse("application/json; charset=utf-8");
  private static String sHttpUrl = "http://49.4.78.2/Thingworx/Things/";

  public static String getDefaultUrl() {
    return sHttpUrl;
  }

  /*public static void setDefaultUrl(String httpUrl) {
    HttpApi.sHttpUrl = httpUrl;
  }*/

  public static String getUserName() {
    return mUserName;
  }

  public static void setUserName(String mUserName) {
    HttpApi.mUserName = mUserName;
  }

  private static OkHttpClient sClient = new OkHttpClient.Builder()
          .connectTimeout(10, TimeUnit.SECONDS)
          .writeTimeout(10, TimeUnit.SECONDS)
          .readTimeout(20, TimeUnit.SECONDS)
          .build();

  public static <T> T requestData(IRequest request, Class<T> clazz) {
    String json = requestString(request);
    LogUtil.d(TAG, "requestData(startTask) --- responseBody  startTask=" + request + ", json = " + json);
    if (json == null) {
      return null;
    }
    T result = null;
    try {
      result = JsonUtil.fromJson(json, clazz);
    } catch (Exception e) {
      LogUtil.e(TAG, "requestData()", e);
      return null;
    }
    if (result == null) {
      LogUtil.e(TAG, "requestData() --- result is null");
      return null;
    }
    LogUtil.d(TAG, "requestData() --- result = " + result);
    return result;
  }

  public static <T> T requestData(String url, Class<T> clazz) {
    String json = requestString(url);
    LogUtil.d(TAG, "requestData(url) --- responseBody  url=" + url + ", json = " + json);
    if (json == null) {
      return null;
    }
    T result = null;
    try {
      result = JsonUtil.fromJson(json, clazz);
    } catch (Exception e) {
      LogUtil.e(TAG, "requestData()", e);
      return null;
    }
    if (result == null) {
      LogUtil.e(TAG, "requestData() --- result is null");
      return null;
    }
    LogUtil.d(TAG, "requestData() --- result = " + result);
    return result;
  }

  public static Bitmap requestBitmap(IRequest iRequest) {
    BitmapInfo bmpResult = requestData(iRequest, BitmapInfo.class);
    LogUtil.d(TAG, "requestBitmap() --- bmpResult = " + bmpResult);

    if (bmpResult == null) {
      LogUtil.w(TAG, "requestBitmap() bmpResult is null");
      return null;
    }
    Bitmap bmp = null;
    String result = null;
    try {
      result = bmpResult.rows.get(0).result;
    } catch (Exception e) {
      LogUtil.e(TAG, "requestBitmap()", e);
    }
    if (result == null) {
      LogUtil.w(TAG, "requestBitmap() result is null");
      return null;
    }
    byte[] temp = Base64.decode(result.getBytes(), Base64.DEFAULT);
    if (temp == null) {
      LogUtil.w(TAG, "requestBitmap() bmpByte is null");
      return null;
    }
    bmp = BitmapUtil.decodeByteArray(temp, BitmapTask.DEFAULT_LENGTH);
    return bmp;
  }

  private static String requestString(IRequest request) {
    ResponseBody body = requestByPost(request);
    if (body != null) {
      try {
        return body.string();
      } catch (IOException e) {
        e.printStackTrace();
        LogUtil.e(TAG, "requestString()", e);
      }
    }
    return null;
  }

  private static String requestString(String url) {
    ResponseBody body = requestByGet(url);
    LogUtil.d(TAG, "requestString() body=" + body);
    if (body != null) {
      try {
        return body.string();
      } catch (IOException e) {
        e.printStackTrace();
        LogUtil.e(TAG, "requestString()", e);
      }
    }
    return null;
  }

  private static ResponseBody requestByPost(IRequest iRequest) {
    if (iRequest == null || iRequest.getUrl() == null) {
      return null;
    }
    String url = sHttpUrl + iRequest.getUrl();
    RequestBody requestBody = null;
    if (!iRequest.isEmptyPostBody()) {
      String jsons = JsonUtil.toJson(iRequest);
      LogUtil.d(TAG, "requestByPost() --- url = " + url + " --- json = " + jsons);
      if (jsons == null) {
        return null;
      }
      requestBody = RequestBody.create(TYPE_JSON, jsons);
    } else {
      LogUtil.d(TAG, "requestByPost() empty requestBody--- url = " + url);
      requestBody = RequestBody.create(null, "");
    }
    final Request request = new Request.Builder()
            .url(url)
            .addHeader("Content-Type", "application/json")
            .addHeader("Accept", "application/json")
            .addHeader("appKey", "f09a249e-9299-4c3c-8f61-24eeb1a0e212")
            .post(requestBody)
            .build();
    try {
      Response response = sClient.newCall(request).execute();
      if (response == null) {
        LogUtil.d(TAG, "requestByPost() --- response is null");
        return null;
      }
      if (!response.isSuccessful()) {
        LogUtil.d(TAG, "requestByPost() --- response is not Successful");
        return null;
      }
      ResponseBody responseBody = response.body();
      if (responseBody == null) {
        LogUtil.d(TAG, "requestByPost() --- responseBody is null");
        return null;
      }
      return responseBody;
    } catch (IOException e) {
      LogUtil.e(TAG, "requestByPost()", e);
      return null;
    }
  }

  private static ResponseBody requestByGet(String url) {
    url = sHttpUrl + url;
    LogUtil.d(TAG, "requestByGet() --- url = " + url);
    final Request request = new Request.Builder()
            .url(url)
            .addHeader("Content-Type", "application/json")
            .addHeader("Accept", "application/json")
            .addHeader("appKey", "f09a249e-9299-4c3c-8f61-24eeb1a0e212")
            .get()
            .build();
    try {
      Response response = sClient.newCall(request).execute();
      if (response == null) {
        LogUtil.d(TAG, "requestByGet() --- response is null");
        return null;
      }
      if (!response.isSuccessful()) {
        LogUtil.d(TAG, "requestByGet() --- response is not Successful");
        return null;
      }
      ResponseBody responseBody = response.body();
      if (responseBody == null) {
        LogUtil.d(TAG, "requestByGet() --- responseBody is null");
        return null;
      }
      return responseBody;
    } catch (IOException e) {
      LogUtil.e(TAG, "requestByGet()", e);
      return null;
    }
  }

}
