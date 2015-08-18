package com.example.lenovo.networktools.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.lenovo.networktools.R;
import com.example.lenovo.networktools.utils.ActHandler;
import com.example.lenovo.networktools.utils.Logger;
import com.example.lenovo.networktools.widget.ftp.FTPServerService;
import com.example.lenovo.networktools.widget.ftp.swiftp.Defaults;
import com.example.lenovo.networktools.widget.ftp.swiftp.Globals;
import com.example.lenovo.networktools.widget.ftp.swiftp.UiUpdater;

import java.io.File;
import java.net.InetAddress;

/**
 * 你的pc和手机在同一个网络中，
 * 手机中开启ftp服务器，pc一般通过浏览器就可以对手机中的文件进行管理
 */
public class FTPServerActivity extends Activity {

    private TextView ip_address;
    private TextView instructionText;
    private TextView instructionTextPre;
    private Button start_ftp_server;
    private TextView wifi_state;

    private ActHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_ftp_server);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        initHandler();
        initView();
    }

    private void initHandler() {
        handler = new ActHandler(FTPServerActivity.this, new ActHandler.HandleMsg() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 0:
                        handler.removeMessages(0);
                        updateUI();
                        break;
                    case 1:
                        handler.removeMessages(1);
                }
            }
        });
    }

    private void initView() {
        ip_address = (TextView) findViewById(R.id.ip_address);
        instructionText = (TextView) findViewById(R.id.instruction);
        instructionTextPre = (TextView) findViewById(R.id.instruction_pre);
        start_ftp_server = (Button) findViewById(R.id.start_ftp_server);
        start_ftp_server.setOnClickListener(myOnClickListener);
        wifi_state = (TextView) findViewById(R.id.wifi_state);
        WifiManager wifiManager = (WifiManager) getSystemService(WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        if (wifiInfo != null)
            wifi_state.setText(wifiInfo.getSSID());

        updateUI();
        UiUpdater.registerClient(handler);
    }

    private void updateUI() {

        boolean running = FTPServerService.isRunning();
        if (running) {

            InetAddress address = FTPServerService.getWifiIp();
            if (address != null) {
                String port = ":" + FTPServerService.getPort();
                ip_address.setText("ftp://" + address.getHostAddress()
                        + (FTPServerService.getPort() == 21 ? "" : port));
                start_ftp_server.setText("停止服务");
            } else {
                // could not get IP address, stop the service
                Intent intent = new Intent(FTPServerActivity.this, FTPServerService.class);
                stopService(intent);
                ip_address.setText("");
            }
        }

        ip_address.setVisibility(running ? View.VISIBLE : View.INVISIBLE);
        instructionText.setVisibility(running ? View.VISIBLE : View.GONE);
        instructionTextPre.setVisibility(running ? View.GONE : View.VISIBLE);
    }

    OnClickListener myOnClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.start_ftp_server:
                    Globals.setLastError(null);
                    File chrootDir = new File(Defaults.chrootDir);
                    Logger.d("ftp server dir=" + chrootDir);
                    if (!chrootDir.isDirectory())
                        return;

                    Intent intent = new Intent(FTPServerActivity.this, FTPServerService.class);

                    Globals.setChrootDir(chrootDir);
                    if (!FTPServerService.isRunning()) {
                        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
                            Logger.d("start Service");
                            startService(intent);
                        }
                    } else {
                        Logger.d("stop Service");
                        stopService(intent);
                        start_ftp_server.setText("启动服务");
                    }
                    break;
            }
        }
    };


    public void onBackPressed() {
        UiUpdater.unregisterClient(handler);
        Intent intent = new Intent(FTPServerActivity.this, FTPServerService.class);
        stopService(intent);
        finish();
    }

}