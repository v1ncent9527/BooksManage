package com.vincent.android.architecture.main.classroom;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.vincent.android.architecture.main.R;


public class VideActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Window window = getWindow();

        //设置修改状态栏
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        //设置状态栏的颜色，和你的app主题或者标题栏颜色设置一致就ok了
        window.setStatusBarColor(Color.parseColor("#666666"));

        setContentView(R.layout.activity_video);

        (findViewById(R.id.back)).setOnClickListener(v -> finish());

        (findViewById(R.id.play)).setOnClickListener(v -> startActivity(new Intent(VideActivity.this, PlayActivity.class)));
    }
}
