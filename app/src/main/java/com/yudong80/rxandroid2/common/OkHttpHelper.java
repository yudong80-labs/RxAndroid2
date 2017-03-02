package com.yudong80.rxandroid2.common;

import android.util.Log;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkHttpHelper {
	private static final String TAG = OkHttpHelper.class.getSimpleName();
	private static OkHttpClient client = new OkHttpClient();
	public static String ERROR = "ERROR";
	
	public static String get(String url) throws IOException { 
		Request request = new Request.Builder()
		        .url(url)
		        .build();
		try {
			Response res = client.newCall(request).execute();
			return res.body().string();
		} catch (IOException e) {
			Log.e(TAG, e.getMessage());
			throw e;
		} 
	}
	
	public static String getWithLog(String url) throws IOException { 
		Log.d(TAG, "OkHttp call URL = " + url);
		return get(url);
	}	
}
