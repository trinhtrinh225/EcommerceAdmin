package com.example.e_commerceadmin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ViewFlipper;

public class Detail_product extends AppCompatActivity {

//    ViewFlipper v_flipper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);

//        int images[] = {R.drawable.product1, R.drawable.product1, R.drawable.product1};

//        v_flipper = findViewById(R.id.v_flipper);

//        //for loop
//        for (int i = 0; i < images.length; i++) {
//            flipperImages(images[i]);
//        }

    }

//    public void flipperImages(int image){
//        ImageView imageView = new ImageView(this);
//        imageView.setBackgroundResource(image);
//
//        v_flipper.addView(imageView);
//        v_flipper.setFlipInterval(4000);
//        v_flipper.setAutoStart(true);
//
//        //animation
//        v_flipper.setInAnimation(this, android.R.anim.slide_in_left);
//        v_flipper.setOutAnimation(this, android.R.anim.slide_out_right);
//    }
}