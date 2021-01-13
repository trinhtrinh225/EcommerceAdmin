package com.example.e_commerceadmin.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.e_commerceadmin.Adapter.CartViewItemHolder;
import com.example.e_commerceadmin.Model.CartModel;
import com.example.e_commerceadmin.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ChiTietDonHang extends AppCompatActivity {

    private RecyclerView list_detail_oders;
    RecyclerView.LayoutManager layoutManager;
    private DatabaseReference productsRef;

    private String userID = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chi_tiet_don_hang_layout);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));
        actionBar.setTitle("Chi Tiết Đơn Hàng");

        list_detail_oders = findViewById(R.id.list_detail_oders);
        list_detail_oders.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        list_detail_oders.setLayoutManager(layoutManager);


        userID = getIntent().getStringExtra("uid");

        productsRef = FirebaseDatabase.getInstance().getReference()
                .child("CartList")
                .child("AdminView")
                .child(userID).child("Products");
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<CartModel> options = new FirebaseRecyclerOptions.Builder<CartModel>()
                .setQuery(productsRef, CartModel.class)
                .build();

        FirebaseRecyclerAdapter<CartModel, CartViewItemHolder> adapter = new FirebaseRecyclerAdapter<CartModel, CartViewItemHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull CartViewItemHolder holder, int position, @NonNull CartModel cartModel) {
                holder.item_CartName.setText(cartModel.getP_name());
                holder.item_CartPrice.setText("Giá: " + cartModel.getP_price_final() + " đ");
                holder.item_CartQuantity.setText("Số lượng:[" + cartModel.getP_quantity() + "]");
            }

            @NonNull
            @Override
            public CartViewItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart_show, parent, false);
                CartViewItemHolder holder = new CartViewItemHolder(view);
                return holder;
            }
        };

        list_detail_oders.setAdapter(adapter);
        adapter.startListening();

    }
}