package com.ptithcm.quanlikho;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;

public class ListNameDevice extends AppCompatActivity {
    ArrayList<String> arrayNameDevice;
    ArrayList<String> arraySTT;
    ArrayList<String> arraymaVT;
    ArrayList<String> arrayDVT;
    ArrayList<String> arrayNoiSx;
    ArrayList<String> arrayChatLuong;
    ArrayList<String> arrayKhoHMG;
    ArrayList<String> arrayKhoHMH;
    ArrayList<String> arrayKhoHML;
    ArrayList<String> arrayKhoHMM;
    ArrayList<String> arrayChoMuon;
    ArrayList<String> arrayTonThucTe;
    ArrayList<String> arraySoSach;

    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_name_device);
        getArray();
        recyclerView = findViewById(R.id.name_device_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(ListNameDevice.this,RecyclerView.VERTICAL,false));
        recyclerView.setAdapter(new NameDeviceAdapter(this,arrayNameDevice,arraySTT));
    }
    private void getArray(){
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("bundle");
        if (bundle != null) {
            arraySTT = bundle.getStringArrayList("arraySTT");
            arraymaVT = bundle.getStringArrayList("arraymaVT");
            arrayNameDevice = bundle.getStringArrayList("arrayNameDevice");
            arrayDVT = bundle.getStringArrayList("arrayDVT");
            arrayNoiSx = bundle.getStringArrayList("arrayNoiSx");
            arrayChatLuong = bundle.getStringArrayList("arrayChatLuong");
            arrayKhoHMG = bundle.getStringArrayList("arrayKhoHMG");
            arrayKhoHMH = bundle.getStringArrayList("arrayKhoHMH");
            arrayKhoHML = bundle.getStringArrayList("arrayKhoHML");
            arrayKhoHMM = bundle.getStringArrayList("arrayKhoHMM");
            arraySoSach = bundle.getStringArrayList("arraySoSach");
            arrayChoMuon = bundle.getStringArrayList("arrayChoMuon");
            arrayTonThucTe = bundle.getStringArrayList("arrayTonThucTe");
        }
    }
}
