package com.example.e_commerceadmin.Activity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.e_commerceadmin.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class UploadFile extends AppCompatActivity {

    Button btnChon, btnHinh;
    ImageView hinhup;
    private static final int PICK_IMAGE_REQUEST = 1;
    Uri uri;
    EditText name;
    private FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customdialogthem);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Thêm danh mục");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setControl();
        setEvent();
    }

    private void setEvent() {

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

        btnChon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputData();
            }
            private String cate_name;
            private void inputData()
            {
                //                1.Nhap du lieu
                cate_name = name.getText().toString().trim();

//         2.Kiem tra du lieu
                if(TextUtils.isEmpty(cate_name))
                {
                    Toast.makeText(UploadFile.this, "Không được bỏ trống tên DM ", Toast.LENGTH_SHORT).show();
                    return;
                }
                addCate();


            }

            private void addCate() {
                progressDialog = new ProgressDialog(UploadFile.this);
                progressDialog.setTitle("Đang thêm danh mục...");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.setMessage("Them dm...");
                progressDialog.show();
                final String timestamp =  " " + System.currentTimeMillis();
                if(uri == null)
                {
                    //upload without image
                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("cateId", "" + timestamp);
                    hashMap.put("cate_name", "" + cate_name);
                    hashMap.put("cate_image", "");
                    hashMap.put("timestamp", "" + timestamp);
                    hashMap.put("uid", "" + firebaseAuth.getUid());
//            add db
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Danhmuc");
                    reference.child(timestamp).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            progressDialog.dismiss();
                            Toast.makeText(UploadFile.this, "Thêm DM Thành Công", Toast.LENGTH_SHORT).show();
                            clearData();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(UploadFile.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })  ;

                }   else {
                    //upload with image
                    //name and path of image to be uploaded
                    String filePathandName = "cate_image/" + "" + timestamp;
                    StorageReference storageReference = FirebaseStorage.getInstance().getReference(filePathandName);
                    storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                    get URL of upload image
                            Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                            while (!uriTask.isSuccessful());
                            Uri dowloadImageUri = uriTask.getResult();
                            if(uriTask.isSuccessful())
                            {
                                //upload without image
                                HashMap<String, Object> hashMap = new HashMap<>();
                                hashMap.put("cateId", "" + timestamp);
                                hashMap.put("cate_name", "" + cate_name);
                                hashMap.put("cate_image", "" + dowloadImageUri);
                                hashMap.put("timestamp", "" + timestamp);
                               // hashMap.put("uid", "" + firebaseAuth.getUid());
//            add db
                                DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Danhmuc");
                                reference.child(timestamp).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        progressDialog.dismiss();
                                        Toast.makeText(UploadFile.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                                        clearData();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        progressDialog.dismiss();
                                        Toast.makeText(UploadFile.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                })  ;
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(UploadFile.this, ""+ e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
            private void clearData()
            {
                //clear data after upload products
                name.setText("");
                uri = null;

            }

        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            uri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                // Log.d(TAG, String.valueOf(bitmap));

                ImageView imageView = (ImageView) findViewById(R.id.hinhup);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void setControl() {
        btnChon = findViewById(R.id.btnChon);
        hinhup = findViewById(R.id.hinhup);
        btnHinh = findViewById(R.id.btnIcon);
        name = findViewById(R.id.ten);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
