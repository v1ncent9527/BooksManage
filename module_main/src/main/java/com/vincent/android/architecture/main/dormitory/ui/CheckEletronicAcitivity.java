package com.vincent.android.architecture.main.dormitory.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.vincent.android.architecture.main.R;

public class CheckEletronicAcitivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_eletronic_acitivity);
    }

    public void checkElectric(View view) {
        startActivity(new Intent(this, ElectricUseActivity.class));

    }

    public void electricRepair(View view) {
        startActivity(new Intent(this, ChamberClockInActivity.class));
    }
}