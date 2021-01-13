package com.example.e_commerceadmin.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_commerceadmin.Model.DonHangModel;
import com.example.e_commerceadmin.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class QuanLyDonHang extends AppCompatActivity {
    private RecyclerView list_Oders;
    private DatabaseReference oderRef;

    public FirebaseAuth firebaseAuth;
    public FirebaseUser firebaseUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quan_ly_don_hang_layout);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));
        actionBar.setTitle("Quản lý đơn hàng");

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        oderRef = FirebaseDatabase.getInstance().getReference().child("CartOders");


        list_Oders = findViewById(R.id.list_Oders);
        list_Oders.setLayoutManager(new LinearLayoutManager(this));
    }

    private String saveCurrentTime, saveCurrentDate;

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<DonHangModel> options = new FirebaseRecyclerOptions.Builder<DonHangModel>()
                .setQuery(oderRef, DonHangModel.class)
                .build();


        FirebaseRecyclerAdapter<DonHangModel, DonHangAdapterViewHolder> adapter = new FirebaseRecyclerAdapter<DonHangModel, DonHangAdapterViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull DonHangAdapterViewHolder holder, final int position, @NonNull final DonHangModel donHangModel) {
                holder.oder_Name.setText("Tên: " + donHangModel.getOder_name());
                holder.oder_Phone.setText("LH: " + donHangModel.getOder_phone());
                holder.oder_Address.setText("Địa chỉ: " + donHangModel.getOder_address());
                holder.oder_CartPriceTotal.setText("Giá đơn hàng: " + donHangModel.getTotal_mount());
                holder.oder_Date_Time.setText("Giờ đặt: " + donHangModel.getTime() + "   " + donHangModel.getDated());

                holder.oder_Button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(QuanLyDonHang.this, ChiTietDonHang.class);
                        intent.putExtra("uid", donHangModel.getUid());
                        startActivity(intent);
                    }
                });

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CharSequence opSequence[] = new CharSequence[]
                                {
                                        "Xóa",
                                        "Tiến Hành Giao"
                                };
                        final AlertDialog.Builder builderA = new AlertDialog.Builder(QuanLyDonHang.this);
                        builderA.setTitle("Xử Lý Đơn Hàng");
                        builderA.setItems(opSequence, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (which == 0) {
                                    CharSequence options[] = new CharSequence[]
                                            {
                                                    "Yes",
                                                    "No"
                                            };
                                    final AlertDialog.Builder builder = new AlertDialog.Builder(QuanLyDonHang.this);
                                    builder.setTitle("Bạn có chắc muốn xóa đơn hàng này?");
                                    builder.setItems(options, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (which == 0) {
                                                final String uID = getRef(position).getKey();
                                                final DatabaseReference refer = FirebaseDatabase.getInstance().getReference("Alarm");
                                                AlertDialog.Builder builder1 = new AlertDialog.Builder(QuanLyDonHang.this);
                                                final View messageLayout = getLayoutInflater().inflate(R.layout.dialog_message, null);
                                                builder1.setView(messageLayout);
                                                builder1.setTitle("Lý do xoá?");

                                                builder1.setPositiveButton("Xóa và Gửi", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        Calendar calendarForDate = Calendar.getInstance();
                                                        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
                                                        saveCurrentDate = currentDate.format(calendarForDate.getTime());

                                                        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
                                                        saveCurrentTime = currentTime.format(calendarForDate.getTime());
                                                        EditText message_edt = messageLayout.findViewById(R.id.message_edt);
                                                        HashMap<String, Object> mesMap = new HashMap<>();
                                                        mesMap.put("uid", donHangModel.getUid());
                                                        mesMap.put("time", saveCurrentTime);
                                                        mesMap.put("dated", saveCurrentDate);
                                                        mesMap.put("time_oder", donHangModel.getTime() + "/" + donHangModel.getDated());
                                                        mesMap.put("price", donHangModel.getTotal_mount());
                                                        mesMap.put("mess", message_edt.getText().toString().trim());
                                                        mesMap.put("phone", donHangModel.getOder_phone());

                                                        refer.child(donHangModel.getUid()).child(donHangModel.getOder_phone()).setValue(mesMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                Toast.makeText(QuanLyDonHang.this, "Bạn đã xóa thành công và gửi tin nhắn cho khách hàng!", Toast.LENGTH_SHORT).show();
                                                            }
                                                        });
                                                        RemoveOders(uID);
                                                        dialog.dismiss();
                                                    }
                                                });
                                                builder1.show();
                                            } else {
                                                dialog.dismiss();
                                            }
                                        }
                                    });
                                    builder.show();
                                }
                                else   {
                                    CharSequence optionsA[] = new CharSequence[]
                                            {
                                                    "Yes",
                                                    "No"
                                            };
                                    final AlertDialog.Builder builderB = new AlertDialog.Builder(QuanLyDonHang.this);
                                    builderB.setTitle("Bạn có muốn xác nhận đơn hàng này?");
                                    builderB.setItems(optionsA, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (which == 0)
                                            {
                                                final String uID = getRef(position).getKey();
                                                final DatabaseReference refer = FirebaseDatabase.getInstance().getReference("AlarmShip");
                                                AlertDialog.Builder builder2 = new AlertDialog.Builder(QuanLyDonHang.this);
                                                final View messageLayout = getLayoutInflater().inflate(R.layout.dialog_message, null);
                                                builder2.setView(messageLayout);
                                                builder2.setTitle("Nhắn gửi khách hàng, xác nhận và chuyển đến bộ phận giao hàng!");

                                                builder2.setPositiveButton("Xác Nhận", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        Calendar calendarForDate = Calendar.getInstance();
                                                        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
                                                        saveCurrentDate = currentDate.format(calendarForDate.getTime());

                                                        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
                                                        saveCurrentTime = currentTime.format(calendarForDate.getTime());
                                                        EditText message_edt = messageLayout.findViewById(R.id.message_edt);
                                                        HashMap<String, Object> mesMap = new HashMap<>();
                                                        mesMap.put("uid", donHangModel.getUid());
                                                        mesMap.put("time", saveCurrentTime);
                                                        mesMap.put("dated", saveCurrentDate);
                                                        mesMap.put("time_oder", donHangModel.getTime() + "/" + donHangModel.getDated());
                                                        mesMap.put("price", donHangModel.getTotal_mount());
                                                        mesMap.put("mess", message_edt.getText().toString().trim());
                                                        mesMap.put("phone", donHangModel.getOder_phone());

                                                        refer.child(donHangModel.getUid()).child(donHangModel.getOder_phone()).setValue(mesMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                Toast.makeText(QuanLyDonHang.this, "Đơn hàng đã được bàn giao cho bộ phận vận chuyển!", Toast.LENGTH_SHORT).show();
                                                            }
                                                        });
                                                        RemoveOders(uID);
                                                        dialog.dismiss();
                                                    }
                                                });
                                                builder2.show();
                                            }
                                            else {
                                                dialog.dismiss();
                                            }
                                        }
                                    });
                                    builderB.show();
                                }
                            }
                        });
                        builderA.show();
                    }
                });
            }

            @NonNull
            @Override
            public DonHangAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart_oders, parent, false);
                return new DonHangAdapterViewHolder(view);
            }
        };

        list_Oders.setAdapter(adapter);
        adapter.startListening();
    }


    public static class DonHangAdapterViewHolder extends RecyclerView.ViewHolder {
        public TextView oder_Name, oder_Phone, oder_CartPriceTotal,
                oder_Address, oder_Date_Time;
        public Button oder_Button;

        public DonHangAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            oder_Name = itemView.findViewById(R.id.oder_Name);
            oder_Phone = itemView.findViewById(R.id.oder_Phone);
            oder_CartPriceTotal = itemView.findViewById(R.id.oder_CartPriceTotal);
            oder_Address = itemView.findViewById(R.id.oder_Address);
            oder_Date_Time = itemView.findViewById(R.id.oder_Date_Time);
            oder_Button = itemView.findViewById(R.id.oder_Button);
        }
    }

    public void RemoveOders(String uID) {
        oderRef.child(uID).removeValue();
    }
}