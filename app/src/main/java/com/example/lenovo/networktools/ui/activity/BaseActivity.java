package com.example.lenovo.networktools.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.lenovo.networktools.R;

/**
 * Created by 10129302 on 15-2-6.
 */
public class BaseActivity extends Activity {

    protected TextView base_text_show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_base);
        base_text_show = (TextView) findViewById(R.id.base_text_show);
    }
}
