package com.vincent.android.architecture.main.dormitory.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vincent.android.architecture.main.R;
import com.vincent.android.architecture.main.dormitory.entity.ChamberClockEntity;

import java.util.ArrayList;
import java.util.List;

public class ChamberClockInActivity extends AppCompatActivity {
    private List<ChamberClockEntity> list = new ArrayList<>();
    private RecyclerView rvData;
    private ClockAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chamber_clock_in);
        rvData = findViewById(R.id.rvData);
        rvData.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapter = new ClockAdapter();
        list.add(new ChamberClockEntity());
        list.add(new ChamberClockEntity());
        list.add(new ChamberClockEntity());
        list.add(new ChamberClockEntity());
        list.add(new ChamberClockEntity());
        list.add(new ChamberClockEntity());
        list.add(new ChamberClockEntity());
        list.add(new ChamberClockEntity());
        rvData.setAdapter(adapter);
    }

    class ClockAdapter extends RecyclerView.Adapter<ClockHolder> {

        @NonNull
        @Override
        public ClockHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ClockHolder(LayoutInflater.from(ChamberClockInActivity.this).inflate(R.layout.item_clock_in, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ClockHolder holder, int position) {
            if (position == 0) {
                holder.edtBed.setText("床位");
                holder.edtName.setText("姓名");
                holder.edtBed.setFocusable(false);
                holder.edtName.setFocusable(false);
                holder.btnClock.setVisibility(View.INVISIBLE);
            } else {
                holder.edtBed.setText("");
                holder.edtName.setText("");
                holder.edtBed.setFocusableInTouchMode(true);
                holder.edtName.setFocusableInTouchMode(true);
                holder.btnClock.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }

    class ClockHolder extends RecyclerView.ViewHolder {
        EditText edtBed, edtName;
        Button btnClock;

        public ClockHolder(@NonNull View itemView) {
            super(itemView);
            edtBed = itemView.findViewById(R.id.edtBed);
            edtName = itemView.findViewById(R.id.edtName);
            btnClock = itemView.findViewById(R.id.btnClock);
        }
    }

}