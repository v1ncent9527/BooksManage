package com.vincent.android.architecture.main.classroom.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.vincent.android.architecture.main.R;


public class CourseDialog extends Dialog implements View.OnClickListener {


    private Context mContext;

    private Button dialog_ok, dialog_back;

    public CourseDialog(Context context) {
        super(context);
        this.mContext = context;
    }

    public CourseDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        this.mContext = context;
    }

    protected CourseDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.mContext = context;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_course);
        setCanceledOnTouchOutside(true);

        dialog_ok = findViewById(R.id.dialog_ok);
        dialog_back = findViewById(R.id.dialog_back);

        dialog_ok.setOnClickListener(this);
        dialog_back.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        this.dismiss();
    }

}
