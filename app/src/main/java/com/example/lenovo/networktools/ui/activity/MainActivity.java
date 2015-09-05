package com.example.lenovo.networktools.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.lenovo.networktools.R;
import com.example.lenovo.networktools.utils.command.GeneralCommand;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GeneralCommand.root();
        initView();
    }

    private void initView() {
        findViewById(R.id.tools_tcp_dump).setOnClickListener(MyBtnClick);
        findViewById(R.id.tools_adb_connect).setOnClickListener(MyBtnClick);
        findViewById(R.id.tools_ftp_server).setOnClickListener(MyBtnClick);
        findViewById(R.id.tools_share_apk).setOnClickListener(MyBtnClick);
        findViewById(R.id.tools_db_inspector).setOnClickListener(MyBtnClick);
        findViewById(R.id.tools_introduction).setOnClickListener(MyBtnClick);
    }

    private View.OnClickListener MyBtnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tools_adb_connect:
                    GeneralCommand.AdbConnect();
                    startActivity(new Intent(MainActivity.this, AdbConnectActivity.class));
                    break;
                case R.id.tools_tcp_dump:
                    startActivity(new Intent(MainActivity.this, TcpDumpActivity.class));
                    break;
                case R.id.tools_ftp_server:
                    startActivity(new Intent(MainActivity.this, FTPServerActivity.class));
                    break;
                case R.id.tools_share_apk:
                    startActivity(new Intent(MainActivity.this, ShareAPKActivity.class));
                    break;
                case R.id.tools_db_inspector:
                    startActivity(new Intent(MainActivity.this, DbInspectorActivity.class));
                    break;
                case R.id.tools_introduction:
                    startActivity(new Intent(MainActivity.this, IntroductionActivity.class));
                    break;
            }
        }
    };
}
