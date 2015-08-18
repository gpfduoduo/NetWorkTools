package com.example.lenovo.networktools.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import com.example.lenovo.networktools.R;
import com.example.lenovo.networktools.ui.fragment.dbinspector.FileFragment;


/**
 * Created by 10129302 on 15-2-15.
 * 数据库查看器：选择你的数据库文件，点击即可查看数据库中的内容和数据库的结构
 * 只处理数据库后缀为.sql .sqlite .db .cblite的文件，一般android中默认为.db文件。
 */
public class DbInspectorActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_dbinspector);

        if (savedInstanceState == null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.dbinspector_container, new FileFragment()).commit();
        }
    }


    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else super.onBackPressed();
    }

}
