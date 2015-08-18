package com.example.lenovo.networktools.ui.activity;

import android.os.Bundle;

/**
 * Created by 10129302 on 15-2-26.
 * 一些介绍性的操作
 */
public class IntroductionActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String introduction = "对于“手机调试”、“手机抓包”功能：首先你的设备必须已经进行了root，否则会出现未知错误，甚至是程序闪退!!!"
                + "\n\n"
                + "对于“FTP服务器”和“FTP服务器”功能，不需要root即可"
                + "\n\n"
                + "关于“数据库查看器”：找到你的数据库文件（如：database.db)，点击即可，如果你需要看data/data下面的数据库，那么需要root";
        base_text_show.setText(introduction);
    }

}
