package com.example.e_commerceadmin.ThongBao;


import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Adapter;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.e_commerceadmin.R;
import com.google.firebase.database.DatabaseReference;

public class PhanHoiKH extends AppCompatActivity {
    DatabaseReference userRefForSeen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phan_hoi_k_h);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Phản hồi khách hàng");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
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