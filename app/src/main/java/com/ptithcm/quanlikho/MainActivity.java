package com.ptithcm.quanlikho;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    ArrayList<String> arraymaVT;
    ArrayList<String> arrayNameDevice;
    ArrayList<String> arraySTT;
    ArrayList<String> arrayDVT;
    ArrayList<String> arrayNoiSx;
    ArrayList<String> arrayChatLuong;
    ArrayList<String> arrayKhoHMG;
    ArrayList<String> arrayKhoHML;
    ArrayList<String> arrayKhoHMH;
    ArrayList<String> arrayKhoHMM;
    ArrayList<String> arraySoSach;
    ArrayList<String> arrayChoMuon;
    ArrayList<String> arrayTonThucTe;
    Button btnSelectFile, btnListDevice, btnDeleteData;
    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnSelectFile = findViewById(R.id.btn_select_file);
        btnListDevice = findViewById(R.id.btn_list_device);
        btnDeleteData = findViewById(R.id.btn_delete_data);

        btnSelectFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectFile();
            }
        });
        btnListDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                putDataAndRecyclerView();
            }
        });
        btnDeleteData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteData();
            }
        });
    }


    private void selectFile() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkFilePermissions();
        }
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        try {
            startActivityForResult(
                    Intent.createChooser(intent, "Select a File to Upload"),
                    1996);
        } catch (ActivityNotFoundException ex) {
            Toast.makeText(MainActivity.this, "Please install a File Manager.", Toast.LENGTH_SHORT).show();
        }
    }

    private void upLoadData() {
         db = FirebaseFirestore.getInstance();
        for (int i = 0; i < arrayNameDevice.size(); i++) {
            Map<String, Object> tonKho = new HashMap<>();

            tonKho.put("MaVT", arraymaVT.get(i));
            tonKho.put("TenVT", arrayNameDevice.get(i));
            tonKho.put("DVT", arrayDVT.get(i));
            tonKho.put("NoiSX", arrayNoiSx.get(i));
            tonKho.put("ChatLuong", arrayChatLuong.get(i));
            tonKho.put("KhoHMG", arrayKhoHMG.get(i));
            tonKho.put("KhoHMH", arrayKhoHMH.get(i));
            tonKho.put("KhoHML", arrayKhoHML.get(i));
            tonKho.put("KhoHMM", arrayKhoHMM.get(i));
            tonKho.put("SoSach", arraySoSach.get(i));
            tonKho.put("ChoMuon", arrayChoMuon.get(i));
            tonKho.put("TonThucTe", arrayTonThucTe.get(i));

            db.collection("HangTonKho").document(arraySTT.get(i))
                    .set(tonKho)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d(TAG, "DocumentSnapshot successfully written!");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "Error writing document", e);
                        }
                    });

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void checkFilePermissions() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            int permissionCheck = this.checkSelfPermission("Manifest.permission.READ_EXTERNAL_STORAGE");
            permissionCheck += this.checkSelfPermission("Manifest.permission.WRITE_EXTERNAL_STORAGE");
            if (permissionCheck != 0) {

                this.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 1001); //Any number
            }
        } else {
            Log.d(TAG, "checkBTPermissions: No need to check permissions. SDK version < LOLLIPOP.");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1996 && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            Log.d(TAG, "File Uri: " + uri.toString());
            arrayNameDevice = new ArrayList<>();
            arraySTT = new ArrayList<>();
            arraymaVT = new ArrayList<>();
            arrayDVT = new ArrayList<>();
            arrayNoiSx = new ArrayList<>();
            arrayChatLuong = new ArrayList<>();
            arrayKhoHMG = new ArrayList<>();
            arrayKhoHML = new ArrayList<>();
            arrayKhoHMH = new ArrayList<>();
            arrayKhoHMM = new ArrayList<>();
            arraySoSach = new ArrayList<>();
            arrayChoMuon = new ArrayList<>();
            arrayTonThucTe = new ArrayList<>();

            String path = null;

            try {
                path = FileUtils.getPath(MainActivity.this, uri);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            Log.d(TAG, "File Path: " + path);
            try {
                File file = new File(path);
                FileInputStream fileInputStream = new FileInputStream(file);
                XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
                XSSFSheet sheet = workbook.getSheetAt(0);
                int rowCount = sheet.getPhysicalNumberOfRows();                     //571 rows
                Log.d(TAG, "rowCount: " + rowCount);
                FormulaEvaluator formulaEvaluator = workbook.getCreationHelper().createFormulaEvaluator();
                StringBuilder sb = new StringBuilder();

                for (int r = 1; r < rowCount; r++) {
                    Row row = sheet.getRow(r);
                    if (row != null) {
                        int cellCount = row.getPhysicalNumberOfCells();
                        for (int c = 0; c < cellCount; c++) {
                            String value = getCellAsString(row, c, formulaEvaluator);

                            switch (c) {
                                case 0:
                                    String value1 = value.substring(0, value.length() - 2);
                                    arraySTT.add(value1);
                                    break;
                                case 1:
                                    arraymaVT.add(String.valueOf(value));
                                    break;
                                case 2:
                                    arrayNameDevice.add(String.valueOf(value));
                                    break;
                                case 3:
                                    arrayDVT.add(String.valueOf(value));
                                    break;
                                case 4:
                                    arrayNoiSx.add(String.valueOf(value));
                                    break;
                                case 5:
                                    arrayChatLuong.add(String.valueOf(value));
                                    break;
                                case 6:
                                    arrayKhoHMG.add(String.valueOf(value));
                                    break;
                                case 7:
                                    arrayKhoHML.add(String.valueOf(value));
                                    break;
                                case 8:
                                    arrayKhoHMH.add(String.valueOf(value));
                                    break;
                                case 9:
                                    arrayKhoHMM.add(String.valueOf(value));
                                    break;
                                case 10:
                                    arraySoSach.add(String.valueOf(value));
                                    break;
                                case 12:
                                    arrayChoMuon.add(String.valueOf(value));
                                    break;
                                case 13:
                                    arrayTonThucTe.add(String.valueOf(value));
                                    break;
                                default:
                            }
                        }
                    } else {
                        Log.e(TAG, "row = null");
                    }
                }
                upLoadData();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private String getCellAsString(Row row, int c, FormulaEvaluator formulaEvaluator) {
        String value = "";
        try {
            Cell cell = row.getCell(c);
            CellValue cellValue = formulaEvaluator.evaluate(cell);
            switch (cellValue.getCellType()) {
                case Cell.CELL_TYPE_STRING:
                    value = cellValue.getStringValue();
                    break;
                case Cell.CELL_TYPE_BLANK:
                    value = " ";
                    break;
                case Cell.CELL_TYPE_NUMERIC:
                    double numricValue = cellValue.getNumberValue();
                    if (HSSFDateUtil.isCellDateFormatted(cell)) {
                        double date = cellValue.getNumberValue();
                        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yy");
                        value = simpleDateFormat.format(HSSFDateUtil.getJavaDate(date));
                    } else {
                        value = numricValue + "";
                    }
                    break;
                default:
            }
        } catch (NullPointerException e) {
            Log.e(TAG, "getCellAsString: NullPointException: " + e.getMessage());
        }
        return value;
    }

    private void putDataAndRecyclerView() {
        Intent intent = new Intent(MainActivity.this, ListNameDevice.class);
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("arraySTT", arraySTT);
        bundle.putStringArrayList("arraymaVT", arraymaVT);
        bundle.putStringArrayList("arrayNameDevice", arrayNameDevice);
        bundle.putStringArrayList("arrayDVT", arrayDVT);
        bundle.putStringArrayList("arrayNoiSx", arrayNoiSx);
        bundle.putStringArrayList("arrayChatLuong", arrayChatLuong);
        bundle.putStringArrayList("arrayKhoHMG", arrayKhoHMG);
        bundle.putStringArrayList("arrayKhoHMH", arrayKhoHMH);
        bundle.putStringArrayList("arrayKhoHML", arrayKhoHML);
        bundle.putStringArrayList("arrayKhoHMM", arrayKhoHMM);
        bundle.putStringArrayList("arraySoSach", arraySoSach);
        bundle.putStringArrayList("arrayName", arraySoSach);
        bundle.putStringArrayList("arrayChoMuon", arrayChoMuon);
        bundle.putStringArrayList("arrayTonThucTe", arrayTonThucTe);

        intent.putExtra("bundle", bundle);
        this.startActivity(intent);
    }

    private void deleteData() {

    }
}



