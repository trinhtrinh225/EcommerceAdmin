package com.example.e_commerceadmin.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_commerceadmin.Model.ProductModel;
import com.example.e_commerceadmin.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RecyAdapterProduct extends RecyclerView.Adapter<RecyAdapterProduct.ViewHolder> {

    private Context context;
    public ArrayList<ProductModel> productModelsList;

    public RecyAdapterProduct(Context context1, ArrayList<ProductModel> productModelsList1)
    {
        this.context = context1;
        this.productModelsList = productModelsList1;
    }



    List<String> discountPriceS;
    List<String> prices;
    List<String> titles;
    List<Integer> images;
    LayoutInflater inflater;

    public RecyAdapterProduct(Context context, List<String> titles, List<Integer> images, List<String> prices, List<String> discountPriceS)
    {
        this.discountPriceS = discountPriceS;
        this.prices = prices;
        this.titles = titles;
        this.images = images;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.from(context).inflate(R.layout.recyclerview_model, parent, false);
        
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductModel productModel = productModelsList.get(position);
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
        //setData
        

        holder.txtTitles.setText(productTitle);
        holder.txtPrices.setText(productPrice);
        holder.txtDiscountPriceS.setText(discountPrice);
        if(discountAvailable.equals("true"))
        {
            //product is discount
            holder.txtDiscountPriceS.setVisibility(View.VISIBLE);
        }
        else {
            holder.txtDiscountPriceS.setVisibility(View.GONE);
        }
        try{
           Picasso.with(context).load(productIcon).placeholder(R.drawable.phone_image).into(holder.imgGridImages);
        }   catch (Exception e){
            holder.imgGridImages.setImageResource(R.drawable.aonu_image);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
            }
        });

    }
    public class ViewHolder extends  RecyclerView.ViewHolder{
        TextView txtTitles, txtPrices, txtDiscountPriceS;
        ImageView imgGridImages;
        public  ViewHolder(View itemView){
            super(itemView);
            txtPrices = itemView.findViewById(R.id.tvPriceRecycler);
            txtTitles = itemView.findViewById(R.id.tvNameRecycler);
            txtDiscountPriceS = itemView.findViewById(R.id.discountPriceS);
            imgGridImages = itemView.findViewById(R.id.imageRecyclerview);
        }
    }

    @Override
    public int getItemCount() {
        return titles.size();
    }
    
}
