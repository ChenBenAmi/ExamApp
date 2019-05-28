package com.example.examapp.ui.image;

import android.content.Context;

import com.example.examapp.ui.base.BasePresenter;

public class ImagePresenter<V extends ImageMvpView> extends BasePresenter<V> implements ImageMvpPresenter<V> {

    private Context mContext;

    public ImagePresenter(Context context) {
        super(context);
        mContext=context;
    }
}
