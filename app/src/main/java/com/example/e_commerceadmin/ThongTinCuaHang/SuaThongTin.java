package com.example.e_commerceadmin.ThongTinCuaHang;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.app.AlertDialog;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_commerceadmin.QuanLySanPham.Sua;
import com.example.e_commerceadmin.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.HashMap;

public class SuaThongTin extends AppCompatActivity {

    ImageView themImageIF;
    EditText themDC, themTG, themSDT, themMoTa;
    Button btnCapNhatSP,btnHinh;

    //    permission constant
    private static final int CAMERA_REQUEST_CODE = 200;
    private static final int STORAGE_REQUEST_CODE = 300;
    //    Image Pick Constant
    private static final int IMAGE_PICK_GALLERY_CODE = 400;
    private static final int IMAGE_PICK_CAMERA_CODE = 500;
    //    Permission Arrays
    private String[] cameraPermission;
    private String[] storagePermission;
    //    Image URI
    private Uri uri;
    //    FireBase
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
private static final int PICK_IMAGE_REQUEST = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sua_thong_tin);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Cập Nhật Thông Tin");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        AnhXa();
        firebaseAuth = FirebaseAuth.getInstance();
        //setup progress dialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Đang cập nhật...");
        progressDialog.setCanceledOnTouchOutside(false);
        cameraPermission = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermission = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};


        //        Discount


        btnHinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openFile();
            }

            private void openFile() {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
            }
        });
        btnCapNhatSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Flow
//                1.Nhap du lieu
//                2.Kiem tra du lieu
//                3.Cap nhat du lieu
                inputData();

            }
        });
        //get id of product

    }


    //========================================Chon danh muc san pham=================================================




    //        intent to pick image from camera



    //handle permisson result
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            uri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                // Log.d(TAG, String.valueOf(bitmap));

                ImageView imageView = (ImageView) findViewById(R.id.imgHinh);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //    Nhap du lieu
    private String DC, TG, SDT, MT;

    private void inputData() {
        //                1.Nhap du li
        DC = themDC.getText().toString().trim();
        TG = themTG.getText().toString().trim();
        SDT = themMoTa.getText().toString().trim();
        MT = themSDT.getText().toString().trim();

//         2.Kiem tra du lieu
        if (TextUtils.isEmpty(DC)) {
            Toast.makeText(this, "Không được bỏ trống địa chỉ ", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(TG)) {
            Toast.makeText(this, "Không được bỏ trống thời gian mở cửa", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(SDT)) {
            Toast.makeText(this, "Không được bỏ trống số điện thoại", Toast.LENGTH_SHORT).show();
            return;
        }
        updateIF();
    }

    private void updateIF() {

        //show progress
        progressDialog.setMessage("Dang cap nhat ...");
        progressDialog.show();
        if (uri == null) {
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("diaChi", "" + DC);
            hashMap.put("thoiGian", "" + TG);
            hashMap.put("SDT", "" + SDT);
            hashMap.put("moTa", "" + MT);

            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("ThongTin");
            reference.child("ThongTin1")
                    .updateChildren(hashMap)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            progressDialog.dismiss();
                            Toast.makeText(SuaThongTin.this, "Cập Nhật Thành Công!", Toast.LENGTH_SHORT).show();

                        }


                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(SuaThongTin.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            String filePathAndName = "product_image/" ;
            StorageReference storageReference = FirebaseStorage.getInstance().getReference(filePathAndName);
            storageReference.putFile(uri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                            while (!uriTask.isSuccessful()) ;
                            Uri dowloadImageUri = uriTask.getResult();
                            if (uriTask.isSuccessful()) {
                                HashMap<String, Object> hashMap = new HashMap<>();
                                hashMap.put("diaChi", "" + DC);
                                hashMap.put("thoiGian", "" + TG);
                                hashMap.put("SDT", "" + SDT);
                                hashMap.put("moTa", "" + MT);
                                hashMap.put("imgHinh", "" + dowloadImageUri);

                                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("ThongTin");
                                reference.child("ThongTin1")
                                        .updateChildren(hashMap)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                progressDialog.dismiss();
                                                Toast.makeText(SuaThongTin.this, "Cap nhat...", Toast.LENGTH_SHORT).show();
                                            }

                                        })

                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                progressDialog.dismiss();
                                                Toast.makeText(SuaThongTin.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        });

                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(SuaThongTin.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }

    }




    private void AnhXa() {
        themImageIF = findViewById(R.id.imgHinh);
        themDC = findViewById(R.id.diaChi);
        themTG = findViewById(R.id.thoiGian);
        themSDT = findViewById(R.id.SDT);
        themMoTa = findViewById(R.id.moTa);
        btnCapNhatSP = findViewById(R.id.btnSua);
        btnHinh = findViewById(R.id.btnHinh);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                return true;

            default:break;
        }
        return super.onOptionsItemSelected(item);
    }
}
