package com.example.e_commerceadmin.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;
import com.example.e_commerceadmin.QLDonHang;
import com.example.e_commerceadmin.QLKhachHang;
import com.example.e_commerceadmin.R;
import com.example.e_commerceadmin.dsden;
import com.example.e_commerceadmin.QuanLySanPham.them;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
public class Trangchu_Admin<FirebaseListAdapter> extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    FloatingActionButton floatingButton;
    ImageView img;
    //Show:
     RecyclerView recyclerView;
     DatabaseReference databaseReference;
     CategoryAdapter cadapter;
     List<Category> list;
     FirebaseDatabase fb;
     FirebaseAuth firebaseAuth;
    //Gallery
    private  static final int PICK_IMAGE_REQUEST = 1;
    //Upload
    private Uri uri;
    private StorageReference storageReference;
    private StorageTask task;
    ImageView hinhup;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trangchu__admin);
        floatingButton = findViewById(R.id.floatingButton);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_menu);
        floatingButton = findViewById(R.id.floatingButton);
        toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        hinhup = findViewById(R.id.hinhup);

        //============================================Model===Category============================================
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        floatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Display();
            }
        });
        showCategory();

    }




    private void showCategory() {
        recyclerView = (RecyclerView) findViewById(R.id.recycle_category);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layout =new LinearLayoutManager(this, GridLayoutManager.VERTICAL, false);
        RecyclerView.LayoutParams params = new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT);
        params.setMargins(0,0,0,0);
        layout.canScrollVertically();
        recyclerView.setLayoutManager(layout);
        list = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference("Danhmuc");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot1) {
                for(DataSnapshot snapshot: snapshot1.getChildren())
                {
                    Category category = snapshot.getValue(Category.class);
                    list.add(category);
                }
               cadapter = new CategoryAdapter(Trangchu_Admin.this, list);
                recyclerView.setAdapter(cadapter);
            }

          @Override
           public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Trangchu_Admin.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void Display() {
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.customdialogradio, null);
        final RadioButton a = (RadioButton) alertLayout.findViewById(R.id.radioButton1);
        final RadioButton b = (RadioButton) alertLayout.findViewById(R.id.radioButton2);
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("THÊM");
        alert.setView(alertLayout);
        alert.setCancelable(false);
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getBaseContext(), "Cancel clicked", Toast.LENGTH_SHORT).show();
            }
        });

        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (a.isChecked()) {
                    Intent a = new Intent(Trangchu_Admin.this, UploadFile.class);
                    startActivity(a);
                }
                if (b.isChecked()) {
                    Intent i = new Intent(Trangchu_Admin.this, them.class);
                    startActivity(i);
                }

            }
        });
        AlertDialog dialog = alert.create();
        dialog.show();
    }

    //============================================NavigationViewMenu==ChooseItem==========================================
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_qldh:
                Toast.makeText(this, "Quản Lý Đơn Hàng", Toast.LENGTH_SHORT).show();
                Intent intent= new Intent(Trangchu_Admin.this, QLDonHang.class);
                startActivity(intent);
                break;
            case R.id.nav_qlkh:
                Toast.makeText(this, "Quản Lý Khách Hàng", Toast.LENGTH_SHORT).show();
                Intent intent1= new Intent(Trangchu_Admin.this, QLKhachHang.class);
                startActivity(intent1);
                break;
            case R.id.nav_dsd:
                Toast.makeText(this, "Danh Sách Đen", Toast.LENGTH_SHORT).show();
                Intent intent2= new Intent(Trangchu_Admin.this, dsden.class);
                startActivity(intent2);
                break;
            case R.id.nav_dmk:
                displayAlertDialog();
                break;
            case R.id.nav_dangxuat:
                Toast.makeText(this, "Đăng Xuất", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(Trangchu_Admin.this, Login_Admin.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
    public void displayAlertDialog() {
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.customdialog, null);
        final EditText mkc = (EditText) alertLayout.findViewById(R.id.mkc);
        final EditText mkm = (EditText) alertLayout.findViewById(R.id.mkm);
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("ĐỔI MẬT KHẨU");
        alert.setView(alertLayout);
        alert.setCancelable(false);
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getBaseContext(), "Cancel clicked", Toast.LENGTH_SHORT).show();
            }
        });

        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // code for matching password
                String mkcu = mkc.getText().toString().trim();
                String mkmoi = mkm.getText().toString().trim();
                if(TextUtils.isEmpty(mkcu))
                {
                    Toast.makeText(getApplication(),   "Nhập mật khẩu cũ", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(mkmoi.length()<6)
                {
                    Toast.makeText(getApplication(),   "Mật khẩu phải hơn 6 kí tự", Toast.LENGTH_SHORT).show();
                    return;
                }
                updatePassword(mkcu,mkmoi);
            }

            private void updatePassword(String mkcu, final String mkmoi) {
                progressDialog.show(Trangchu_Admin.this, "Đổi mật khẩu", "loading....", true);
                final FirebaseUser user = firebaseAuth.getInstance().getCurrentUser();

                AuthCredential authCredential = EmailAuthProvider.getCredential(user.getEmail(), mkcu);
                user.reauthenticate(authCredential)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                user.updatePassword(mkmoi)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                progressDialog.dismiss();
                                                Toast.makeText(getApplication(),   "Password update...." , Toast.LENGTH_SHORT).show();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                progressDialog.dismiss();
                                                Toast.makeText(getApplication(),   "" +e.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressDialog.dismiss();
                                Toast.makeText(getApplication(),   "" +e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

            }
        });
        AlertDialog dialog = alert.create();
        dialog.show();
    }

}
