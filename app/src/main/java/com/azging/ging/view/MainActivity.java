package com.azging.ging.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.azging.ging.R;
import com.azging.ging.base.IActivity;
import com.azging.ging.utils.Log;

import butterknife.BindView;

public class MainActivity extends AppCompatActivity implements IActivity {
    
    @BindView(R.id.text) TextView a;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int initView() {
        Log.w("main", "aaaaa");
        return R.layout.activity_main;
    }

    @Override
    public void initData() {
        a.setText("gggggg");
    }
}
