package com.example.e_commerceadmin.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_commerceadmin.Activity.DetailProduct;
import com.example.e_commerceadmin.Model.ProductModel;
import com.example.e_commerceadmin.QuanLySanPham.UpdateProduct;
import com.example.e_commerceadmin.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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
        final ProductModel productModel = productList.get(position);

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
        try {
            Picasso.with(context).load(productIcon).placeholder(R.drawable.photoload).into(holder.imageRecyclerview);
        } catch (Exception e) {
            holder.imageRecyclerview.setImageResource(R.drawable.aonu_image);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //show item details
                //Toast.makeText(context, "Hello", Toast.LENGTH_SHORT).show();
                detailsBottomSheet(productModel);
            }
        });
    }

    private void detailsBottomSheet(final ProductModel productModel) {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        //inflate view for bottomsheet
        View view = LayoutInflater.from(context).inflate(R.layout.chitiet_sp, null);
        //set view to bottom sheet
        bottomSheetDialog.setContentView(view);

         
        //init views of bottomsheet
        ImageButton btnBack = view.findViewById(R.id.btnBack);
        Button btnDeleteD = view.findViewById(R.id.btnDeleteD);
        Button btnUpdateD = view.findViewById(R.id.btnUpdateD);
        ImageView imgDetail = view.findViewById(R.id.imgDetail);
        TextView productNameD = view.findViewById(R.id.productNameD);
        TextView productPriceD = view.findViewById(R.id.productPriceD);
        TextView productDescriptionD = view.findViewById(R.id.productDescriptionD);
        TextView productCategoryD = view.findViewById(R.id.productCategoryD);
        TextView productQuantityD = view.findViewById(R.id.productQuantityD);
        TextView discountPriceD = view.findViewById(R.id.discountPriceD);


        //getData
        final String id = productModel.getProductID();
        String uid = productModel.getUid();
        String discountAvailable = productModel.getDiscountAvailable();
        String discountPrice = productModel.getDiscountPrice();
        final String productTitle = productModel.getProductTitle();
        String productQuantity = productModel.getProductQuantity();
        String productCategory = productModel.getProductCategory();
        String productDescription = productModel.getProductDescription();
        String productPrice = productModel.getProductPrice();
        String productIcon = productModel.getProductIcon();
        final String stimetamp = productModel.getTimestamp();

        //setData
        productNameD.setText("Tên sản phẩm: " + productTitle);
        productPriceD.setText("Giá sản phẩm: " + productPrice);
        productDescriptionD.setText("Mô tả: " + productDescription);
        productCategoryD.setText("Danh mục: " + productCategory);
        productQuantityD.setText("Số lượng: " + productQuantity);
        discountPriceD.setText(discountPrice);
        discountPriceD.setVisibility(View.VISIBLE);
        try {
            Picasso.with(context).load(productIcon).placeholder(R.drawable.photoload).into(imgDetail);
        } catch (Exception e) {
            imgDetail.setImageResource(R.drawable.aonu_image);
        }

        //show dialog
        bottomSheetDialog.show();

        //edit click
        btnUpdateD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //open  update  activity
                bottomSheetDialog.dismiss();
                Intent intent = new Intent(context, UpdateProduct.class);
                intent.putExtra("productID", stimetamp);
                context.startActivity(intent);
            }
        });

        //delete click
        btnDeleteD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
                //show delete dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Xóa!")
                        .setMessage("Bạn có chắc chắn muốn xóa sản phẩm " + productTitle + " ?")
                        .setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //delete
                                deleteProduct(stimetamp);
                            }
                        }).setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //cancel
                        dialog.dismiss();
                    }
                }).show();

            }
        });

        //back btn
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
            }
        });


    }

    private void deleteProduct(String ids) {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Products");
        reference.child(ids).removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(context, "Xóa Thành Công!", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "Không Xóa Được!...Kiểm tra lại" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    @Override
    public Filter getFilter() {
        if (filter == null) {
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
