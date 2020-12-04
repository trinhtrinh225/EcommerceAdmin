package com.example.e_commerceadmin.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.e_commerceadmin.Adapter.RecyAdapterProduct;
import com.example.e_commerceadmin.QLDonHang;
import com.example.e_commerceadmin.QLKhachHang;
import com.example.e_commerceadmin.R;
import com.example.e_commerceadmin.dsden;
import com.example.e_commerceadmin.QuanLySanPham.them;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;
public class Trangchu_Admin<FirebaseListAdapter> extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    FloatingActionButton floatingButton;
    //Show:
     RecyclerView recyclerView;
     DatabaseReference databaseReference;
     private CategoryAdapter cadapter;
     List<Category> list;

    private RecyclerView recycle_product;
    private List<String> titles;
    private List<String> prices;
    private List<Integer> images;
    private RecyAdapterProduct recyAdapterProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trangchu__admin);
        AnhXa();
        setSupportActionBar(toolbar);
        titles = new ArrayList<>();
        prices = new ArrayList<>();
        images = new ArrayList<>();
        titles.add("Hellow");
        prices.add("12000");
        images.add(R.drawable.phone_image);
        titles.add("Hellow");
        prices.add("12000");
        images.add(R.drawable.phone_image);
        titles.add("Hellow");
        prices.add("12000");
        images.add(R.drawable.phone_image);
        titles.add("Hellow");
        prices.add("12000");
        images.add(R.drawable.phone_image);
        titles.add("Hellow");
        prices.add("12000");
        images.add(R.drawable.phone_image);
        titles.add("Hellow");
        prices.add("12000");
        images.add(R.drawable.phone_image);
        titles.add("Hellow");
        prices.add("12000");
        images.add(R.drawable.phone_image);
        titles.add("Hellow");
        prices.add("12000");
        images.add(R.drawable.phone_image);
        titles.add("Hellow");
        prices.add("12000");
        images.add(R.drawable.phone_image);
        titles.add("Hellow");
        prices.add("12000");
        images.add(R.drawable.phone_image);
        recyAdapterProduct = new RecyAdapterProduct(getApplicationContext(), titles, images, prices);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2, GridLayoutManager.VERTICAL, false);
        recycle_product.setLayoutManager(gridLayoutManager);
        recycle_product.setAdapter(recyAdapterProduct);



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

    private void AnhXa() {
        floatingButton = findViewById(R.id.floatingButton);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_menu);
        floatingButton = findViewById(R.id.floatingButton);
        toolbar = findViewById(R.id.toolBar);
        recycle_product = findViewById(R.id.recycle_product);
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
                // code for matching password
                if (a.isChecked()) {
                    Dialog1();
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

    private void Dialog1() {
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.customdialogthem, null);
        final EditText ten = (EditText) alertLayout.findViewById(R.id.ten);
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("THÊM DANH MỤC");
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
                String user = ten.getText().toString();
                Toast.makeText(getBaseContext(), user, Toast.LENGTH_SHORT).show();
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
        final EditText etUsername = (EditText) alertLayout.findViewById(R.id.mkc);
        final EditText etPassword = (EditText) alertLayout.findViewById(R.id.mkm);
        final EditText cbShowPassword = (EditText) alertLayout.findViewById(R.id.nmkm);
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
                String user = etUsername.getText().toString();
                String pass = etPassword.getText().toString();
                Toast.makeText(getBaseContext(),  user  + pass, Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog dialog = alert.create();
        dialog.show();
    }

}
