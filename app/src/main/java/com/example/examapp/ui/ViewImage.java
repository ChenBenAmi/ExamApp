package com.example.examapp.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.example.examapp.R;
import com.github.chrisbanes.photoview.PhotoView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewImage extends AppCompatActivity {

    @BindView(R.id.myImage)
    PhotoView myImage;

    String url = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_image);
        ButterKnife.bind(this);
//
        url = getIntent().getStringExtra("image_url");

        Glide.with(getApplicationContext())
                .load(url)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(myImage);
    }
}
