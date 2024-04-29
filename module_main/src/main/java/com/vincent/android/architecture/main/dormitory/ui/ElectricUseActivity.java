package com.vincent.android.architecture.main.dormitory.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vincent.android.architecture.main.R;
import com.vincent.android.architecture.main.dormitory.entity.ElectricEntity;

import java.util.ArrayList;
import java.util.List;

public class ElectricUseActivity extends AppCompatActivity {
    private List<ElectricEntity> list = new ArrayList<>();
    private RecyclerView rvData;
    private ElectricAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eletronic_use);
        adapter = new ElectricAdapter();
        rvData = findViewById(R.id.rvData);
        rvData.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        list.add(new ElectricEntity());
        list.add(new ElectricEntity());
        list.add(new ElectricEntity());
        list.add(new ElectricEntity());
        list.add(new ElectricEntity());
        list.add(new ElectricEntity());
        rvData.setAdapter(adapter);
    }

    public void payElectric(View view) {
        startActivity(new Intent(this, PayElectronicActivity.class));
    }

    class ElectricAdapter extends RecyclerView.Adapter<ElectricHolder> {

        @NonNull
        @Override
        public ElectricHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ElectricHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_electric, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ElectricHolder holder, int position) {
            holder.edtDate.setText("");
            holder.edtElectric.setText("");
            holder.edtAmount.setText("");
            holder.edtAmount.setFocusableInTouchMode(true);
            holder.edtDate.setFocusableInTouchMode(true);
            holder.edtElectric.setFocusableInTouchMode(true);
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }

    class ElectricHolder extends RecyclerView.ViewHolder {
        EditText edtDate, edtElectric, edtAmount;

        public ElectricHolder(@NonNull View itemView) {
            super(itemView);
            edtDate = itemView.findViewById(R.id.edtDate);
            edtElectric = itemView.findViewById(R.id.edtBattery);
            edtAmount = itemView.findViewById(R.id.edtAmount);
        }
    }

}