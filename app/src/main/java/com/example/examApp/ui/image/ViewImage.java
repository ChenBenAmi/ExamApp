package com.example.examApp.ui.image;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.examApp.R;
import com.github.chrisbanes.photoview.PhotoView;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.examApp.ui.home.recyclerview.HeroesPresenter.HERO_NAME;
import static com.example.examApp.ui.home.recyclerview.HeroesPresenter.HERO_URL;

public class ViewImage extends AppCompatActivity implements ImageMvpView {


    ImagePresenter<ViewImage> mImagePresenter;

    @BindView(R.id.myImage)
    PhotoView myImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_image);
        ButterKnife.bind(this);
        mImagePresenter = new ImagePresenter<>(this);
        mImagePresenter.onAttach(this);

        setImage();


    }

    @Override
    public void setImage() {
        String url = getIntent().getStringExtra(HERO_URL);
        String heroName = getIntent().getStringExtra(HERO_NAME);
        setTitle(heroName);
        Glide.with(getApplicationContext())
                .load(url)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(myImage);
    }
    @Override
    protected void onDestroy() {
        mImagePresenter.onDetach();
        super.onDestroy();
    }
}
