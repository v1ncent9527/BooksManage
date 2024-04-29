package com.vincent.android.architecture.main.dormitory.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.vincent.android.architecture.main.R;

public class CheckEnvironmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_environment);
    }

    public void tempr(View view) {
        startActivity(new Intent(this, EnvironmentDetailActivity.class));
    }

    public void air(View view) {
        startActivity(new Intent(this, EnvironmentDetailActivity.class));

    }

    public void control(View view) {
        startActivity(new Intent(this, ControlActivity.class));
    }
}