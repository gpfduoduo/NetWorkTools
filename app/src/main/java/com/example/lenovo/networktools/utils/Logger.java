package com.example.lenovo.networktools.utils;

import android.util.Log;

/**
 * Created by 郭攀峰 10129302 on 14-8-28.
 * adb 查看打印log日志的控制类，isLog = true,显示日志，反之不显示日志
 */

public class Logger {

	private static boolean isLog = true;
	private static final String TAG = "net_tools";

	public static void setLog(boolean isLog) {
		Logger.isLog = isLog;
	}

	public static void on() {
		isLog = true;
	}
	public static void off() {
		isLog = false;
	}
	
	public static boolean getIsLog() {
		return isLog;
	}

	public static void d(String tag, String msg) {
		if (isLog) {
			Log.d(tag, msg);
		}
	}

	public static void d(String msg) {
		Log.d(TAG, msg);
	}

	public static void d(String tag, String msg, Throwable tr) {
		if (isLog) {
			Log.d(tag, msg, tr);
		}
	}

	public static void e(Throwable tr) {
		if (isLog) {
			Log.e(TAG, "", tr);
		}
	}

	public static void i(String msg) {
		if (isLog) {
			Log.i(TAG, msg);
		}
	}
	
	public static void i(String tag, String msg) {
		if (isLog) {
			Log.i(tag, msg);
		}
	}

	public static void i(String tag, String msg, Throwable tr) {
		if (isLog) {
			Log.i(tag, msg, tr);
		}

	}

	public static void e(String tag, String msg) {
		if (isLog) {
			Log.e(tag, msg);
		}
	}

	public static void e(String msg) {
		if (isLog) {
			Log.e(TAG, msg);
		}
	}

	public static void e(String tag, String msg, Throwable tr) {
		if (isLog) {
			Log.e(tag, msg, tr);
		}
	}
	
	public static void e(String msg, Throwable tr) {
		if (isLog) {
			Log.e(TAG, msg, tr);
		}
	}

	public static void systemErr(String msg) {
		// if (true) {
		if (isLog) {
			if (msg != null) {
				Log.e(TAG, msg);
			}

		}
	}

}
