package com.example.e_commerceadmin.ThongTinCuaHang;

import android.content.Context;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_commerceadmin.R;
import com.example.e_commerceadmin.ThongTinCuaHang.Information;
import com.squareup.picasso.Picasso;
import java.util.List;

public class InfoAdapter extends RecyclerView.Adapter<InfoAdapter.ImageViewHolder> {
    private Context acontext;
    private List<Information> ainformation;


    public InfoAdapter(Context acontext, List<Information> information)
    {
        this.acontext = acontext;
        this.ainformation = information;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(acontext).inflate(R.layout.re_info, parent, false);
        return new ImageViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int i) {
        Information information = ainformation.get(i);
        holder.tvdc.setText(information.getDiaChi());
        holder.tvtg.setText(information.getThoiGian());
        holder.tvsdt.setText(information.getSDT());
        holder.tvmt.setText(information.getMoTa());
        Picasso.with(acontext)
                .load(information.getImgHinh())
                .placeholder(R.drawable.logo).fit()
                .centerCrop()
                .into(holder.imgHinh);
    }

    @Override
    public int getItemCount() {
        return ainformation.size();
    }



    public class ImageViewHolder extends RecyclerView.ViewHolder{
        TextView tvdc;
        TextView tvtg;
        TextView tvsdt;
        TextView tvmt;
        ImageView imgHinh;
        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            tvdc = (TextView) itemView.findViewById(R.id.tvdc);
            tvtg = (TextView) itemView.findViewById(R.id.tvtg);
            tvmt = (TextView) itemView.findViewById(R.id.tvmt);
            tvsdt = (TextView) itemView.findViewById(R.id.tvsdt);
            imgHinh = (ImageView) itemView.findViewById(R.id.imgHinh);
        }
    }
}
