package com.example.lenovo.networktools.ui.activity;

import android.os.Bundle;

import com.example.lenovo.networktools.utils.NetUtil;

/**
 * Created by 10129302 on 15-2-6.
 * 使得你已经root的手机可以通过在PC上的命令行操作：
 * adb connect "your device ip"
 * 来进行adb调试
 */
public class AdbConnectActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String ip = NetUtil.getLocalIp(AdbConnectActivity.this);
        base_text_show.setText("本机IP地址：" + ip + "\n" +
                "请在PC进行adb connect " + ip + ":5555");
    }

}
