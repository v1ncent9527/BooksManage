package com.vincent.android.architecture.main.dormitory.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.vincent.android.architecture.main.R;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;

import java.util.ArrayList;
import java.util.List;

public class ChamberOptionActivity extends AppCompatActivity {
    private Banner bn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chamber_option);
        bn = findViewById(R.id.bn);
        List list = new ArrayList();
        list.add(R.drawable.scheme1);
        list.add(R.drawable.scheme2);
        list.add(R.drawable.scheme3);
        bn.setDatas(list);
        bn.setAdapter(new BannerImageAdapter(list) {
            @Override
            public void onBindView(Object holder, Object data, int position, int size) {
                Glide.with(ChamberOptionActivity.this).load(list.get(position)).into(((BannerImageHolder) holder).imageView);
            }
        }, true).start();
    }

    public void doorSys(View view) {
        startActivity(new Intent(this, DoorSystemActivity.class));
    }

    public void environment(View view) {
        startActivity(new Intent(this, CheckEnvironmentActivity.class));
    }

    public void electric(View view) {
        startActivity(new Intent(this, CheckEletronicAcitivity.class));
    }

    public void chamber(View view) {
        startActivity(new Intent(this, CheckChamberActivity.class));
    }
}