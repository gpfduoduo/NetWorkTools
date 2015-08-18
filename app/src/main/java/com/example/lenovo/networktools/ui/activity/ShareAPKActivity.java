package com.example.lenovo.networktools.ui.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lenovo.networktools.R;
import com.example.lenovo.networktools.utils.Logger;
import com.example.lenovo.networktools.utils.NetUtil;
import com.example.lenovo.networktools.utils.accesspoint.AccessPointManager;
import com.example.lenovo.networktools.utils.accesspoint.Constant;
import com.example.lenovo.networktools.utils.httpserver.HttpServer;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.File;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Random;

/**
 * Created by 10129302 on 15-2-10.
 * 讲该程序分享给其他人
 * 其他人扫描二维码即可
 */
public class ShareAPKActivity extends Activity implements AccessPointManager.OnWifiApStateChangeListener {

    private AccessPointManager mWifiApManager = null;
    private ProgressDialog mProgressDialog;
    private TextView download_apk = null;
    private HttpServer httpServer = null;
    private ImageView qr_image = null;
    private Handler mHandler = new Handler();
    private TextView wifi_hot_spot_name;

    private Bitmap mBitmap = null;

    private boolean isWiFiActive = false;
    private String FREE_SERVER = null;

    private LinearLayout invite_qr_linear;
    private LinearLayout invite_linear;
    private Button next;

    private Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_apk);

        if (NetUtil.isWiFiConnect(ShareAPKActivity.this)) {
            isWiFiActive = true;
        } else isWiFiActive = false;

        initCommonView();
        initWifiShare();
    }

    private void initCommonView() {
        invite_qr_linear = (LinearLayout) findViewById(R.id.invite_qr_linear);
        invite_qr_linear.setVisibility(View.GONE);
        invite_linear = (LinearLayout) findViewById(R.id.invite_linear);
        invite_linear.setVisibility(View.GONE);
        next = (Button) findViewById(R.id.nextstep);
        next.setOnClickListener(mOnClickListener);
        next.setVisibility(View.GONE);
        wifi_hot_spot_name = (TextView) findViewById(R.id.wifi_hotspot_name);
    }

    private void initWifiShare() {
        qr_image = (ImageView) findViewById(R.id.imag);
        download_apk = (TextView) findViewById(R.id.download_apk);
        if (!isWiFiActive) { //建立热点，取得网管ip作为服务器ip地址
            mWifiApManager = new AccessPointManager(ShareAPKActivity.this);
            mWifiApManager.setWifiApStateChangeListener(this);
            createAccessPoint();
        } else { //存在WiFi，以当前的ip作为服务器的ip地址
            FREE_SERVER = NetUtil.getLocalIp(ShareAPKActivity.this);
            String path = getApplicationPath();
            stopHTTPServer();
            startHTTPServer(path, Constant.HTTP_Port);

            invite_linear.setVisibility(View.VISIBLE);
            next.setVisibility(View.VISIBLE);
            wifi_hot_spot_name.setText(NetUtil.getCurrentSSID(ShareAPKActivity.this));
        }
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.nextstep:
                    next.setVisibility(View.GONE);
                    invite_qr_linear.setVisibility(View.VISIBLE);
                    break;
            }
        }
    };

    private void createAccessPoint() {
        mWifiApManager.createWifiApSSID(Constant.WIFI_HOT_SPOT_SSID_PREFIX +
                android.os.Build.MODEL +
                "-" + random.nextInt(1000));
        showProgressDialog("等待", "正在创建WiFi热点");
        if (mWifiApManager.startWifiAp()) {
        } else {
            dismissProgressDialog();
            onBackPressed();
        }
    }

    private void showProgressDialog(String title, String msg) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setCancelable(false);
        }
        mProgressDialog.setTitle(title);
        mProgressDialog.setMessage(msg);
        mProgressDialog.show();
        mProgressDialog.setCanceledOnTouchOutside(true);
    }

    private void dismissProgressDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void onWifiStateChanged(int wifiApState) {
        if (wifiApState == AccessPointManager.WIFI_AP_STATE_ENABLED) {
            onBuildWifiApSuccess();
        } else if (AccessPointManager.WIFI_AP_STATE_FAILED == wifiApState) {
            onBuildWifiApFailed();
        }
    }

    private void onBuildWifiApSuccess() {
        invite_linear.setVisibility(View.VISIBLE);
        next.setVisibility(View.VISIBLE);
        dismissProgressDialog();
        FREE_SERVER = mWifiApManager.getWiFiHotSpotGate();//GPF 2013-12-19 modify
        wifi_hot_spot_name.setText(mWifiApManager.getWifiApSSID());
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                String path = getApplicationPath();
                if (!TextUtils.isEmpty(path)) {
                    FREE_SERVER = mWifiApManager.getWiFiHotSpotGate();//GPF 2013-12-19 modify
                    startHTTPServer(path, Constant.HTTP_Port);
                } else {
                    onBackPressed();
                }
            }
        }, Constant.START_SERVER_DELAY_TIME_MS);
    }

    private String getApplicationPath() {
        String path = null;
        try {
            path = getPackageManager().getApplicationInfo(ShareAPKActivity.this.getPackageName(), 0).sourceDir;
            Log.e("", "###path=" + path);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return path;
    }

    private void startHTTPServer(String path, int port) {
        stopHTTPServer();
        try {
            httpServer = new HttpServer(path, port);
            String keyString = "http://" + FREE_SERVER + ":" + Constant.HTTP_Port + File.separator + getString(R.string.app_name) + ".apk";
            createQR(keyString);
            download_apk.setText(keyString);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public void createQR(String text) {
        try {// 需要引入core包
            if (text == null || "".equals(text) || text.length() < 1) {
                return;
            }
            // 把输入的文本转为二维码
            Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            BitMatrix bitMatrix = new QRCodeWriter().encode(text, BarcodeFormat.QR_CODE,
                    Constant.QR_WIDTH, Constant.QR_HEIGHT, hints);
            int[] pixels = new int[Constant.QR_WIDTH * Constant.QR_HEIGHT];
            for (int y = 0; y < Constant.QR_HEIGHT; y++) {
                for (int x = 0; x < Constant.QR_WIDTH; x++) {
                    if (bitMatrix.get(x, y)) {
                        pixels[y * Constant.QR_WIDTH + x] = 0xff000000;
                    } else {
                        pixels[y * Constant.QR_WIDTH + x] = 0xffffffff;
                    }
                }
            }

            mBitmap = Bitmap.createBitmap(Constant.QR_WIDTH, Constant.QR_HEIGHT,
                    Bitmap.Config.ARGB_8888);
            mBitmap.setPixels(pixels, 0, Constant.QR_WIDTH, 0, 0,
                    Constant.QR_WIDTH, Constant.QR_HEIGHT);
            qr_image.setImageBitmap(mBitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    private void onBuildWifiApFailed() {
        dismissProgressDialog();
        onBackPressed();
    }

    public void onDestroy() {
        super.onDestroy();
        if (!isWiFiActive)
            closeAccessPoint();
        stopHTTPServer();
    }

    private void closeAccessPoint() {
        if (mWifiApManager.isWifiApEnabled()) {
            mWifiApManager.stopWifiAp(false);
            mWifiApManager.destroy(this);
        }
    }

    private void stopHTTPServer() {
        if(httpServer != null) {
            Logger.d("stop http server");
            httpServer.stop();
            httpServer = null;
        }
    }

}
