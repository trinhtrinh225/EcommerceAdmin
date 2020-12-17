package com.example.e_commerceadmin.Adapter;

import android.content.Context;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_commerceadmin.Activity.Trangchu_Admin;
import com.example.e_commerceadmin.Model.Category;
import com.example.e_commerceadmin.R;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ImageViewHolder> {
    private Context acontext;
    private List<Category> acategories;

    Trangchu_Admin trangchu;

    public CategoryAdapter(Context acontext, List<Category> acategories)
    {
        this.acontext = acontext;
        this.acategories = acategories;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(acontext).inflate(R.layout.lv_category, parent, false);
        return new ImageViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int i) {
        final Category category = acategories.get(i);
        holder.tvName.setText(category.getCate_name());
        Picasso.with(acontext)
                .load(category.getCate_image())
                .placeholder(R.drawable.ao).fit()
                .centerCrop()
                .into(holder.igIcon);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String selected = category.getCate_name();
//                trangchu.loadFilterProduct(selected);
                Snackbar.make(v, "Danh mục sản phẩm!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return acategories.size();
    }



    public class ImageViewHolder extends RecyclerView.ViewHolder{
        TextView tvName;
        ImageView igIcon;
        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.ten);
            igIcon = (ImageView) itemView.findViewById(R.id.hinh);
        }
    }




    public class SpaceDividerItemDecoration extends RecyclerView.ItemDecoration {

        private final int mSpaceHeight;
        private boolean mShowLastDivider;

        public SpaceDividerItemDecoration(int spaceHeight, boolean showLastDivider) {
            this.mSpaceHeight = spaceHeight;
            mShowLastDivider = showLastDivider;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                                   RecyclerView.State state) {
            boolean isLastItem = parent.getChildAdapterPosition(view) == parent.getAdapter().getItemCount() - 1;
            if (!isLastItem || mShowLastDivider) {
                outRect.bottom = mSpaceHeight;
            }
        }
    }
}
