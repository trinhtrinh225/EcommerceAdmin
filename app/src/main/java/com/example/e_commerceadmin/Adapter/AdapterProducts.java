package com.example.e_commerceadmin.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_commerceadmin.Model.ProductModel;
import com.example.e_commerceadmin.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterProducts extends RecyclerView.Adapter<AdapterProducts.HolderProducts> implements Filterable {

    private Context context;
    public ArrayList<ProductModel> productList, filterList;
    private FilterProducts filter;

    public AdapterProducts(Context context, ArrayList<ProductModel> productList) {
        this.context = context;
        this.productList = productList;
        this.filterList = productList;
    }

    @NonNull
    @Override
    public HolderProducts onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflate layout
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_model, parent, false);
        return new HolderProducts(view);
    }
    

    @Override
    public void onBindViewHolder(@NonNull HolderProducts holder, int position) {
        //get data
        ProductModel productModel = productList.get(position);

        String id = productModel.getProductID();
        String uid = productModel.getUid();
        String discountAvailable = productModel.getDiscountAvailable();
        String discountPrice = productModel.getDiscountPrice();
        String productTitle = productModel.getProductTitle();
        String productQuantity = productModel.getProductQuantity();
        String productCategory = productModel.getProductCategory();
        String productDescription = productModel.getProductDescription();
        String productPrice = productModel.getProductPrice();
        String productIcon = productModel.getProductIcon();
        String stimetamp = productModel.getTimestamp();

        //set data
        holder.tvNameRecycler.setText(productTitle);
        holder.tvPriceRecycler.setText(productPrice);
        holder.discountPriceS.setText(discountPrice);
        holder.discountPriceS.setVisibility(View.VISIBLE);
//        if(discountAvailable.equals("true"))
//        {
//            //product is discount
//            holder.discountPriceS.setVisibility(View.VISIBLE);
//        }
//        else {
//            holder.discountPriceS.setVisibility(View.GONE);
//        }
        try{
            Picasso.with(context).load(productIcon).placeholder(R.drawable.ao).into(holder.imageRecyclerview);
        }   catch (Exception e){
            holder.imageRecyclerview.setImageResource(R.drawable.aonu_image);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //show item details
                Toast.makeText(context, "Hellow", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    @Override
    public Filter getFilter() {
        if(filter == null)
        {
            filter = new FilterProducts(this, filterList);
        }
        return filter;
    }


    class HolderProducts extends RecyclerView.ViewHolder {
        
        private ImageView imageRecyclerview;
        private TextView tvPriceRecycler;
        private TextView tvNameRecycler;
        private TextView discountPriceS;
        

        public HolderProducts(@NonNull View itemView) {
            super(itemView);

            imageRecyclerview = itemView.findViewById(R.id.imageRecyclerview);
            tvPriceRecycler = itemView.findViewById(R.id.tvPriceRecycler);
            tvNameRecycler = itemView.findViewById(R.id.tvNameRecycler);
            discountPriceS = itemView.findViewById(R.id.discountPriceS);
        }
    }
}
