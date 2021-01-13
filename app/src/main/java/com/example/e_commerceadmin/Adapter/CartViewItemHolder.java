package com.example.e_commerceadmin.Adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_commerceadmin.R;


public class CartViewItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView item_CartName, item_CartQuantity, item_CartPrice;


    public CartViewItemHolder(@NonNull View itemView) {
        super(itemView);
        item_CartName = itemView.findViewById(R.id.item_CartName);
        item_CartQuantity = itemView.findViewById(R.id.item_CartQuantity);
        item_CartPrice = itemView.findViewById(R.id.item_CartPrice);
    }

    @Override
    public void onClick(View v) {
    }
}
