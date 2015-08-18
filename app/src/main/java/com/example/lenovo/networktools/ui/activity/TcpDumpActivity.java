package com.example.lenovo.networktools.ui.activity;

import android.os.Bundle;
import android.os.Environment;

/**
 * Created by 10129302 on 15-2-6.
 * 运行后，pc端通过命令行就可以抓包，
 * 抓包后内容放在sdcard中，然后通过ftp服务就可以将抓包后的内容copy到pc中进行分析
 */
public class TcpDumpActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        base_text_show.setText("手机必须已经root, 首先执行Adb Connect命令" + "\n"
        + "PC端cmd运行: " + "/data/local/activity_tcpdump -i any -p -s 0 -w " + Environment.getExternalStorageDirectory() + "/capture.pcap");
    }
}
