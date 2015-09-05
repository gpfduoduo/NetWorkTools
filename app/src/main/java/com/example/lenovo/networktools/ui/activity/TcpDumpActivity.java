package com.example.lenovo.networktools.ui.activity;


import java.io.File;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.TextView;

import com.example.lenovo.networktools.R;
import com.example.lenovo.networktools.utils.command.GeneralCommand;


/**
 * Created by 10129302 on 15-2-6. 运行后，pc端通过命令行就可以抓包，
 * 抓包后内容放在sdcard中，然后通过ftp服务就可以将抓包后的内容copy到pc中进行分析
 */
public class TcpDumpActivity extends BaseActivity
{
    private TextView tcpdump_text;

    private String savePath = Environment.getExternalStorageDirectory() + File.separator;

    private long time;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        base_text_show.setText("手机必须已经root, 首先执行Adb Connect命令" + "\n" + "PC端cmd运行: "
            + "/data/local/tcpdump -i any -p -s 0 -w "
            + Environment.getExternalStorageDirectory() + "/capture.pcap");

        setContentView(R.layout.activity_tcpdump);

        tcpdump_text = (TextView) findViewById(R.id.tcpdump_text);
    }

    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.tcp_dump_start :
                tcpdump_text.setText("正在抓包...");
                time = System.currentTimeMillis();
                GeneralCommand.startTcpDump(savePath + time + ".pcap");
                break;
            case R.id.tcp_dump_stop :
                GeneralCommand.stopTcpDump();
                tcpdump_text.setText("本次抓包文件保存在：\r\n" + savePath + time + ".pcap");
                break;
        }
    }
}
