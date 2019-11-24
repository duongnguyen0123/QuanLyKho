package com.ptithcm.quanlikho;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class Infor_Device extends AppCompatActivity {
    TextView tvTenVt;
    TextView tvMaVt;
    TextView tvNoiSx;
    TextView tvChatLuong;
    TextView tvKhoHMG;
    TextView tvKhoHML;
    TextView tvKhoHMH;
    TextView tvKhoHMM;
    TextView tvSoSach;
    TextView tvChoMuon;
    TextView tvTonThucTe;
    String tenVt;
    String maVt;
    String noiSx;
    String chatLuong;
    String khoHMG;
    String khoHML;
    String khoHMH;
    String khoHMM;
    String soSach;
    String choMuon;
    String tonTT;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infor__device);

        tvTenVt = findViewById(R.id.tv_infor_tenVT);
        tvMaVt = findViewById(R.id.tv_infor_maVT);
        tvChatLuong = findViewById(R.id.tv_infor_chat_luong_VT);
        tvNoiSx = findViewById(R.id.tv_infor_noiSx);
        tvKhoHMG = findViewById(R.id.tv_infor_khoHMG);
        tvKhoHML = findViewById(R.id.tv_infor_khoHML);
        tvKhoHMH = findViewById(R.id.tv_infor_khoHMH);
        tvKhoHMM = findViewById(R.id.tv_infor_khoHMM);
        tvChoMuon = findViewById(R.id.tv_infor_cho_muon);
        tvSoSach = findViewById(R.id.tv_infor_so_sach);
        tvTonThucTe = findViewById(R.id.tv_infor_ton_TT);

        getDataFromAdapter();

        tvTenVt.setText(tenVt);
        tvMaVt.setText(maVt);
        tvNoiSx.setText(noiSx);
        tvChatLuong.setText(chatLuong);
        tvKhoHMH.setText(khoHMH);
        tvKhoHMG.setText(khoHMG);
        tvKhoHML.setText(khoHML);
        tvKhoHMM.setText(khoHMM);
        tvSoSach.setText(soSach);
        tvTonThucTe.setText(tonTT);
        tvChoMuon.setText(choMuon);
    }
    private void getDataFromAdapter(){
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("BundleInforDevice");

        if (bundle != null) {
            tenVt = bundle.getString("tenVt");
            maVt = bundle.getString("maVt");
            noiSx = bundle.getString("noiSx");
            chatLuong = bundle.getString("chatLuong");
            khoHMG = bundle.getString("khoHMG");
            khoHML = bundle.getString("khoHML");
            khoHMH = bundle.getString("khoHMH");
            khoHMM = bundle.getString("khoHMM");
            soSach = bundle.getString("soSach");
            choMuon = bundle.getString("choMuon");
            tonTT =bundle.getString("tonTT");
        }
    }
}
