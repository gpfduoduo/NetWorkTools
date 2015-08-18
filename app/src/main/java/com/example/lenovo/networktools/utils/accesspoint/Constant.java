package com.example.lenovo.networktools.utils.accesspoint;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;

import com.example.lenovo.networktools.ui.NetToolsApp;

/**
 * Created by 10129302 on 15-2-10.
 */
public class Constant {

    public static final String WIFI_HOT_SPOT_SSID_PREFIX = "NetTools-";
    public static final int HTTP_Port = 8192;
    public static final long START_SERVER_DELAY_TIME_MS = 500;

    public static final String FREE_SERVER = "192.168.43.1"; // GPF 2013-12-19

    /**
     * QR image width and height
     */
    public static final int QR_WIDTH = 400;
    public static final int QR_HEIGHT = 400;


    /** if the device is belong to HTC */
    public static boolean isHTC() {
        boolean isHtc = false;
        if (Build.MODEL.contains("htc") || Build.MODEL.contains("HTC")) {
            isHtc = true;
        }
        return isHtc;
    }

    public static String getCurrentSSID() {
        WifiManager wifiMan = (WifiManager) (NetToolsApp.getAppContext()
                .getSystemService(Context.WIFI_SERVICE));
        WifiInfo wifiInfo = wifiMan.getConnectionInfo();

        if (wifiInfo != null)
            return wifiInfo.getSSID();
        else
            return null;
    }


}
