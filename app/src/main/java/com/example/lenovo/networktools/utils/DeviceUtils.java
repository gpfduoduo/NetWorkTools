package com.example.lenovo.networktools.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * 系统版本信息类
 * Created by 郭攀峰 10129302 on 14-9-5.
 * 
 */
public class DeviceUtils {

	/** >=2.2 */
	public static boolean hasFroyo() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO;
	}

	/** >=2.3 */
	public static boolean hasGingerbread() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD;
	}

	/** >=3.0 LEVEL:11 */
	public static boolean hasHoneycomb() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
	}

	/** >=3.1 */
	public static boolean hasHoneycombMR1() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1;
	}

	/** >=4.0 14 */
	public static boolean hasICS() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH;
	}

	/**SDK version*/
	public static int getSDKVersionInt() {
		return Build.VERSION.SDK_INT;
	}

	public static String getSDKVersion() {
		return Build.VERSION.SDK;
	}

	/**
	 * 判断是否是平板电脑
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isTablet(Context context) {
		return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
	}

	public static boolean isHoneycombTablet(Context context) {
		return hasHoneycomb() && isTablet(context);
	}

	/**
	 * 获得设备型号
	 *
	 * @return
	 */
	public static String getDeviceModel() {
		return StringUtil.trim(Build.MODEL);
	}

	/** 检测是否魅族手机 */
	@SuppressLint("DefaultLocale")
	public static boolean isMeizu() {
		return getDeviceModel().toLowerCase(Locale.US).indexOf("meizu") != -1;
	}

	/** 检测是否HTC手机 */
	@SuppressLint("DefaultLocale")
	public static boolean isHTC() {
		return getDeviceModel().toLowerCase(Locale.US).indexOf("htc") != -1;
	}

	@SuppressLint("DefaultLocale")
	public static boolean isXiaomi() {
		return getDeviceModel().toLowerCase(Locale.US).indexOf("xiaomi") != -1;
	}

	/**
	 * 获得设备制造商
	 * 
	 * @return
	 */
	public static String getManufacturer() {
		return StringUtil.trim(Build.MANUFACTURER);
	}

	public static int getScreenHeight(Context context) {
		Display display = ((WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		return display.getHeight();
	}

	/** 获取屏幕宽度 */
	public static int getScreenWidth(Context context) {
		Display display = ((WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		return display.getWidth();
	}

	/**
	 * 获得设备屏幕密度
	 */
	public static float getScreenDensity(Context context) {
		DisplayMetrics metrics = context.getApplicationContext().getResources()
				.getDisplayMetrics();
		return metrics.density;
	}

	public static int[] getScreenSize(int w, int h, Context context) {
		int phoneW = getScreenWidth(context);
		int phoneH = getScreenHeight(context);

		if (w * phoneH > phoneW * h) {
			phoneH = phoneW * h / w;
		} else if (w * phoneH < phoneW * h) {
			phoneW = phoneH * w / h;
		}

		return new int[] { phoneW, phoneH };
	}

	public static int[] getScreenSize(int w, int h, int phoneW, int phoneH) {
		if (w * phoneH > phoneW * h) {
			phoneH = phoneW * h / w;
		} else if (w * phoneH < phoneW * h) {
			phoneW = phoneH * w / h;
		}
		return new int[] { phoneW, phoneH };
	}

	/** 设置屏幕亮度 */
	public static void setBrightness(final Activity context, float f) {
		WindowManager.LayoutParams lp = context.getWindow().getAttributes();
		lp.screenBrightness = f;
		if (lp.screenBrightness > 1.0f)
			lp.screenBrightness = 1.0f;
		else if (lp.screenBrightness < 0.01f)
			lp.screenBrightness = 0.01f;
		context.getWindow().setAttributes(lp);
	}

	/** 隐藏软键盘 */
	public static void hideSoftInput(Context ctx, View v) {
		InputMethodManager imm = (InputMethodManager) ctx
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		// 这个方法可以实现输入法在窗口上切换显示，如果输入法在窗口上已经显示，则隐藏，如果隐藏，则显示输入法到窗口上
		imm.hideSoftInputFromWindow(v.getApplicationWindowToken(),
				InputMethodManager.HIDE_NOT_ALWAYS);
	}

	/** 显示软键盘 */
	public static void showSoftInput(Context ctx) {
		InputMethodManager imm = (InputMethodManager) ctx
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);// (v,
		// InputMethodManager.SHOW_FORCED);
	}

	/**
	 * 软键盘是否已经打开
	 * 
	 * @return
	 */
	protected boolean isHardKeyboardOpen(Context ctx) {
		return ctx.getResources().getConfiguration().hardKeyboardHidden == Configuration.HARDKEYBOARDHIDDEN_NO;
	}

	public static String getCpuInfo() {
		String cpuInfo = "";
		try {
			if (new File("/proc/cpuinfo").exists()) {
				FileReader fr = new FileReader("/proc/cpuinfo");
				BufferedReader localBufferedReader = new BufferedReader(fr,
						8192);
				cpuInfo = localBufferedReader.readLine();
				localBufferedReader.close();

				if (cpuInfo != null) {
					cpuInfo = cpuInfo.split(":")[1].trim().split(" ")[0];
				}
			}
		} catch (IOException e) {
		} catch (Exception e) {
		}
		return cpuInfo;
	}

	public static void startApkActivity(final Context ctx, String packageName) {
		PackageManager pm = ctx.getPackageManager();
		PackageInfo pi;
		try {
			pi = pm.getPackageInfo(packageName, 0);
			Intent intent = new Intent(Intent.ACTION_MAIN, null);
			intent.addCategory(Intent.CATEGORY_LAUNCHER);
			intent.setPackage(pi.packageName);

			List<ResolveInfo> apps = pm.queryIntentActivities(intent, 0);

			ResolveInfo ri = apps.iterator().next();
			if (ri != null) {
				String className = ri.activityInfo.name;
				intent.setComponent(new ComponentName(packageName, className));
				ctx.startActivity(intent);
			}
		} catch (NameNotFoundException e) {
		}
	}

	/**
	 *  像素 转换成 dp
	 *
	 * @param pxValue
	 *            像素
	 * @returndp
	 */
	public static int px2dip(Context ctx, int pxValue) {
		final float scale = ctx.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}
	/**
	 *  素 成 sp
	 * @returnsp
	 */
	public static int px2sp(Context ctx, int px) {
		float scaledDensity = ctx.getResources().getDisplayMetrics().scaledDensity;
		return (int) (px / scaledDensity);
	}
	/**
	 *  dip 成素 px
	 *
	 * @param dipValue

	 *            dip 像素的值

	 * @return 素px

	 */
	public static int dip2px(Context ctx, float dipValue) {
		final float scale = ctx.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}
    /**
     * 系统中是否含有改activity
     * @param context 上下文
     * @param intent intent意图
     * @return 含有返回true
     */
    public static boolean hasApplication(Context context, Intent intent) {
        PackageManager packageManager = context.getPackageManager();
        List<ResolveInfo> activities = packageManager.queryIntentActivities(intent, 0);
        return activities.size() > 0;
    }

    /**
     * SDcard是否可用
     */
	public static boolean hasExternalStorage() {
		return Environment.MEDIA_MOUNTED_READ_ONLY.equals(Environment
				.getExternalStorageState())
				|| Environment.MEDIA_MOUNTED.equals(Environment
						.getExternalStorageState());
	}

    /**
     * 获取打包是的meta-data值
     * @param context
     * @return
     */
    public static boolean isPhone(Context context) {
        boolean isPhone = true;
        try {
            ApplicationInfo ai = context.getPackageManager().getApplicationInfo(
                    context.getPackageName(), PackageManager.GET_META_DATA);
            if (ai == null) {
                Logger.e("error ai == null");
            } else {
                Bundle bundle = ai.metaData;
                if (bundle == null) {
                    Logger.e(" error bundle == null");
                } else {
                    Object value = bundle.get("channel");
                    if (value != null) {
                        String apkChannel = value.toString();
                        Logger.e("apk channel info=" + apkChannel);
                        if (apkChannel.toLowerCase(Locale.US).contains("tv")) {
                            isPhone = false;
                        }
                    } else {
                        Logger.e("get apk channel error");
                    }
                }
            }
        } catch (Exception e) {
            Logger.e("error:" + e.toString());
        }

        return isPhone;
    }
}
