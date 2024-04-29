package com.vincent.android.architecture.main.dormitory.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.vincent.android.architecture.main.R;

public class DoorSystemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_door_system);
    }

    public void repairDoor(View view) {
        startActivity(new Intent(this, DoorRepairActivity.class));
    }


}