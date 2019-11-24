package com.ptithcm.quanlikho;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class NameDeviceAdapter extends RecyclerView.Adapter<NameDeviceAdapter.MainHolder> {
    Context mContext;
    private ArrayList<String> arrayName;
    private ArrayList<String> arraySTT;

    public NameDeviceAdapter(Context mContext, ArrayList<String> arrayName, ArrayList<String> arraySTT) {
        this.mContext = mContext;
        this.arrayName = arrayName;
        this.arraySTT = arraySTT;
    }

    @NonNull
    @Override
    public MainHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MainHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.namedevicelistlayout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MainHolder holder, int position) {
        holder.tv_name.setText(arrayName.get(position));
        holder.tv_stt.setText(String.valueOf(arraySTT.get(position)));
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View v, int position, boolean isLongClick) {
                if(isLongClick){

                }else{
                    FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();
                    mFirestore.collection("HangTonKho").document(arraySTT.get(position)).get()
                            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()){
                                        DocumentSnapshot document = task.getResult();
                                        if (document != null && document.exists()) {
                                            String tenVt = document.getString("TenVT");
                                            String maVt = document.getString("MaVT");
                                            String noiSx = document.getString("NoiSX");
                                            String chatLuong = document.getString("ChatLuong");
                                            String khoHMG = document.getString("KhoHMG");
                                            String khoHML = document.getString("KhoHML");
                                            String khoHMH = document.getString("KhoHMH");
                                            String khoHMM = document.getString("KhoHMM");
                                            String soSach = document.getString("SoSach");
                                            String choMuon = document.getString("ChoMuon");
                                            String tonTT = document.getString("TonThucTe");

                                            Intent intent = new Intent(mContext,Infor_Device.class);
                                            Bundle bundle = new Bundle();
                                            bundle.putString("tenVt",tenVt);
                                            bundle.putString("maVt",maVt);
                                            bundle.putString("noiSx",noiSx);
                                            bundle.putString("chatLuong",chatLuong);
                                            bundle.putString("khoHMG",khoHMG);
                                            bundle.putString("khoHML",khoHML);
                                            bundle.putString("khoHMH",khoHMH);
                                            bundle.putString("khoHMM",khoHMM);
                                            bundle.putString("soSach",soSach);
                                            bundle.putString("choMuon",choMuon);
                                            bundle.putString("tonTT",tonTT);
                                            intent.putExtra("BundleInforDevice",bundle);
                                            mContext.startActivity(intent);
                                        }
                                    }
                                }
                            });
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayName.size();
    }

    class MainHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        TextView tv_name;
        TextView tv_stt;
        ItemClickListener itemClickListener;

        public MainHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_name_device);
            tv_stt = itemView.findViewById(R.id.tv_stt);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }
        public void setItemClickListener(ItemClickListener itemClickListener){
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(v,getAdapterPosition(),false );
        }

        @Override
        public boolean onLongClick(View v) {
            itemClickListener.onClick(v,getAdapterPosition(),true);
            return true;
        }
    }

    public interface ItemClickListener {
        void onClick(View v, int position, boolean isLongClick);
    }
}
