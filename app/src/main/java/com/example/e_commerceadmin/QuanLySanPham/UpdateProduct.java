package com.example.e_commerceadmin.QuanLySanPham;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_commerceadmin.Model.ListCategory;
import com.example.e_commerceadmin.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class UpdateProduct extends AppCompatActivity {

    private String productID;

    ImageView themImageSP;
    EditText themTenSP, themGiaSP, themSLSP, themMoTaSP, themDiscount;
    TextView themDanhMucSP;
    SwitchCompat discountSwitch;
    Button btnCapNhatSP, btnHuy;

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
    private Uri image_Uri;
    //    FireBase
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.capnhat_sp);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Cập Nhật Sản Phẩm ");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        AnhXa();
        firebaseAuth = FirebaseAuth.getInstance();
        //setup progress dialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Đang cập nhật sản phẩm...");
        progressDialog.setCanceledOnTouchOutside(false);
        cameraPermission = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermission = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
        themDiscount.setVisibility(View.GONE);

        //        Discount
        discountSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //show discount ra
                    themDiscount.setVisibility(View.VISIBLE);
                } else {
                    //uncheck
                    themDiscount.setVisibility(View.GONE);
                }
            }
        });

        themImageSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowImagePickDialog();
            }
        });
        themDanhMucSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //pick category
                categoryDialog();
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
        productID = getIntent().getStringExtra("productID");


        loadProductDetails(); //to set product
    }

    private void loadProductDetails() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Products");
        reference.child(productID)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String productID = "" + snapshot.child("productID").getValue();
                        String productTitle = "" + snapshot.child("productTitle").getValue();
                        String productPrice = "" + snapshot.child("productPrice").getValue();
                        String productQuantity = "" + snapshot.child("productQuantity").getValue();
                        String productCategory = "" + snapshot.child("productCategory").getValue();
                        String productDescription = "" + snapshot.child("productDescription").getValue();
                        String productIcon = "" + snapshot.child("productIcon").getValue();
                        String discountPrice = "" + snapshot.child("discountPrice").getValue();
                        String timestamp = "" + snapshot.child("timestamp").getValue();
                        String uid = "" + snapshot.child("uid").getValue();
                        String discountAvailable = "" + snapshot.child("discountAvailable").getValue();

                        //setData to views
                        if (discountAvailable.equals(true)) {
                            discountSwitch.setChecked(true);
                            themDiscount.setVisibility(View.VISIBLE);
                        } else {
                            discountSwitch.setChecked(false);
                            themDiscount.setVisibility(View.GONE);
                        }

                        themTenSP.setText(productTitle);
                        themGiaSP.setText(productPrice);
                        themDanhMucSP.setText(productCategory);
                        themSLSP.setText(productQuantity);
                        themMoTaSP.setText(productDescription);
                        themDiscount.setText(discountPrice);
                        try {
                            Picasso.with(getApplicationContext()).load(productIcon).placeholder(R.drawable.ao).into(themImageSP);
                        } catch (Exception e) {
                            themImageSP.setImageResource(R.drawable.ao);
                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    //========================================Chon danh muc san pham=================================================
    private void categoryDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("ProductCategory").setItems(ListCategory.productCategories, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String category = ListCategory.productCategories[which];
                //cap nhat lai danh muc da chon
                themDanhMucSP.setText(category);
            }
        }).show();
    }

    private void ShowImagePickDialog() {
//        Option to display in dialog
        String[] options = {"camera", "gallery"};
//        dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pick Image").setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    //camera click
                    if (checkCameraPermission()) {
                        PickFromCamera();
                    } else {
                        requestCameraPermissin();
                    }
                } else {
                    //gallery click
                    if (checkStoragePermission()) {
                        PickFromGallery();
                    } else {
                        requestStoragePermissin();
                    }
                }
            }
        }).show();
    }

    //        intent to pick image from gallery
    private void PickFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_GALLERY_CODE);
    }

    //        intent to pick image from camera
    private void PickFromCamera() {
        // using mediaStorage to Pick high/original quality Image
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.Images.Media.TITLE, "Temp_Image_Tittle");
        contentValues.put(MediaStore.Images.Media.DESCRIPTION, "Temp_Image_Description");
        image_Uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, image_Uri);
        startActivityForResult(intent, IMAGE_PICK_CAMERA_CODE);
    }

    private boolean checkStoragePermission() {
        boolean result = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return result;
    }

    private void requestStoragePermissin() {
        ActivityCompat.requestPermissions(this, storagePermission, STORAGE_REQUEST_CODE);
    }

    private boolean checkCameraPermission() {
        boolean result = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == (PackageManager.PERMISSION_GRANTED);
        boolean result1 = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return result && result1;
    }

    private void requestCameraPermissin() {
        ActivityCompat.requestPermissions(this, cameraPermission, CAMERA_REQUEST_CODE);
    }

    //handle permisson result
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case CAMERA_REQUEST_CODE: {
                if (grantResults.length > 0) {
                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean storageAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    if (cameraAccepted && storageAccepted) {
                        PickFromCamera();
                    } else {
                        Toast.makeText(this, "Bắt buộc máy ảnh và quyền lưu trữ", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            case STORAGE_REQUEST_CODE: {
                if (grantResults.length > 0) {
                    boolean storageAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (storageAccepted) {
                        PickFromGallery();
                    } else {
                        Toast.makeText(this, "Bắt buộc quyền lưu trữ", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    //handle image pick result

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == IMAGE_PICK_GALLERY_CODE) {
                image_Uri = data.getData();
                themImageSP.setImageURI(image_Uri);
            } else if (requestCode == IMAGE_PICK_CAMERA_CODE) {
                themImageSP.setImageURI(image_Uri);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    //    Nhap du lieu
    private String productTitle, productDescription, productCategory, productQuantity, productPrice, discountPrice;
    private boolean discountAvailable = false;

    private void inputData() {
        //                1.Nhap du lieu
        productTitle = themTenSP.getText().toString().trim();
        productCategory = themDanhMucSP.getText().toString().trim();
        productQuantity = themSLSP.getText().toString().trim();
        productDescription = themMoTaSP.getText().toString().trim();
        productPrice = themGiaSP.getText().toString().trim();
        discountPrice = themDiscount.getText().toString().trim();
        discountAvailable = discountSwitch.isChecked(); //true/false

//         2.Kiem tra du lieu
        if (TextUtils.isEmpty(productTitle)) {
            Toast.makeText(this, "Không được bỏ trống tên SP ", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(productPrice)) {
            Toast.makeText(this, "Không được bỏ trống giá SP", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(productCategory)) {
            Toast.makeText(this, "Bạn phải chọn một danh mục", Toast.LENGTH_SHORT).show();
            return;
        }
        if (discountAvailable) {
            //product is with discount
            discountPrice = themDiscount.getText().toString().trim();
            if (TextUtils.isEmpty(discountPrice)) {
                Toast.makeText(this, "Bat buoc nhap", Toast.LENGTH_SHORT).show();
                return;
            }
        } else {
            //product is without discount
            discountPrice = "0";
        }
        updateProduct();
    }

    private void updateProduct() {
        //show progress
        progressDialog.setMessage("Dang cap nhat san pham...");
        progressDialog.show();
        if (image_Uri == null) {
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("productTitle", "" + productTitle);
            hashMap.put("productPrice", "" + productPrice);
            hashMap.put("productQuantity", "" + productQuantity);
            hashMap.put("productCategory", "" + productCategory);
            hashMap.put("productDescription", "" + productDescription);
            hashMap.put("discountPrice", "" + discountPrice);

            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Products");
            reference.child(productID)
                    .updateChildren(hashMap)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            progressDialog.dismiss();
                            Toast.makeText(UpdateProduct.this, "Cập Nhật Thành Công!", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(UpdateProduct.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            String filePathAndName = "product_image/" + "" + productID;
            StorageReference storageReference = FirebaseStorage.getInstance().getReference(filePathAndName);
            storageReference.putFile(image_Uri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                            while (!uriTask.isSuccessful()) ;
                            Uri dowloadImageUri = uriTask.getResult();
                            if (uriTask.isSuccessful()) {
                                HashMap<String, Object> hashMap = new HashMap<>();
                                hashMap.put("productTitle", "" + productTitle);
                                hashMap.put("productPrice", "" + productPrice);
                                hashMap.put("productQuantity", "" + productQuantity);
                                hashMap.put("productCategory", "" + productCategory);
                                hashMap.put("productDescription", "" + productDescription);
                                hashMap.put("discountPrice", "" + discountPrice);
                                hashMap.put("productIcon", "" + dowloadImageUri);

                                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Products");
                                reference.child(productID)
                                        .updateChildren(hashMap)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                progressDialog.dismiss();
                                                Toast.makeText(UpdateProduct.this, "Cap nhat...", Toast.LENGTH_SHORT).show();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                progressDialog.dismiss();
                                                Toast.makeText(UpdateProduct.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(UpdateProduct.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


    private void AnhXa() {
        themImageSP = findViewById(R.id.themImageSP);
        themTenSP = findViewById(R.id.themTenSP);
        themGiaSP = findViewById(R.id.themGiaSP);
        themSLSP = findViewById(R.id.themSLSP);
        btnHuy = findViewById(R.id.btnHuy);
        themMoTaSP = findViewById(R.id.themMoTaSP);
        themDiscount = findViewById(R.id.themDiscount);
        discountSwitch = findViewById(R.id.discountSwitch);
        btnCapNhatSP = findViewById(R.id.btnCapNhatSP);
        themDanhMucSP = findViewById(R.id.themDanhMucSP);
    }
}