package com.vincent.android.architecture.main.classroom;

import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.vincent.android.architecture.main.R;

public class PlayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        //加载指定的视频文件
        String path = "android.resource://" + getPackageName() + "/" + R.raw.v1;

        VideoView videoView = findViewById(R.id.videoView);
        videoView.setVideoPath(path);

        //创建MediaController对象
        MediaController mediaController = new MediaController(this);

        //VideoView与MediaController建立关联
        videoView.setMediaController(mediaController);

        //让VideoView获取焦点
        videoView.requestFocus();

        videoView.start();
    }
}
