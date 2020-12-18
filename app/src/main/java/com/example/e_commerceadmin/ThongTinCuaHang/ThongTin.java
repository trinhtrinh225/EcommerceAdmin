package com.example.e_commerceadmin.ThongTinCuaHang;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.e_commerceadmin.Activity.Category;
import com.example.e_commerceadmin.Activity.CategoryAdapter;
import com.example.e_commerceadmin.Activity.Trangchu_Admin;
import com.example.e_commerceadmin.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ThongTin extends AppCompatActivity {
Button btnSua;
private RecyclerView recycle_info;
List<Information> list;
FirebaseDatabase fb;
FirebaseAuth firebaseAuth;
InfoAdapter cadapter;

DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Thông tin cửa hàng");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setControl();
        setEvent();

    }

    private void setEvent() {
        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(ThongTin.this, SuaThongTin.class);
                startActivity(intent);
            }
        });
        showInfo();
    }
    private void showInfo() {


        recycle_info = (RecyclerView) findViewById(R.id.recycle_info);
        recycle_info.setHasFixedSize(true);

        RecyclerView.LayoutManager layout = new LinearLayoutManager(this, GridLayoutManager.VERTICAL, false);
        RecyclerView.LayoutParams params = new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 0, 0, 0);
        layout.canScrollVertically();
        recycle_info.setLayoutManager(layout);
        list = new ArrayList<>();

        databaseReference = FirebaseDatabase.getInstance().getReference("ThongTin");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot1) {
                for (DataSnapshot snapshot : snapshot1.getChildren()) {
                    Information information = snapshot.getValue(Information.class);
                    list.clear();
                    list.add(information);
                }
                cadapter = new InfoAdapter(ThongTin.this, list);
                recycle_info.setAdapter(cadapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ThongTin.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void setControl() {
        btnSua= findViewById(R.id.btnSua1);


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