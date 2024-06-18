package com.vincent.android.architecture.main.classroom.fragment;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.vincent.android.architecture.main.R;
import com.vincent.android.architecture.main.classroom.dialog.CourseDialog;

public class CourseFragment extends Fragment {
    RelativeLayout crl;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Window window = requireActivity().getWindow();

        //设置修改状态栏
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        //设置状态栏的颜色，和你的app主题或者标题栏颜色设置一致就ok了
        window.setStatusBarColor(Color.parseColor("#fafafa"));
        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.fragment_dashboard, null);

        crl = view.findViewById(R.id.crl);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        crl.setOnClickListener(v -> {
            CourseDialog courseDialog = new CourseDialog(requireContext(), R.style.mdialog);
            courseDialog.show();
        });
    }

}