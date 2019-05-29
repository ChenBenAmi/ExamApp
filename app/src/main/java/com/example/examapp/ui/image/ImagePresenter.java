package com.example.examapp.ui.image;

import android.content.Context;
import com.example.examapp.ui.base.BasePresenter;

class ImagePresenter<V extends ImageMvpView> extends BasePresenter<V> implements ImageMvpPresenter<V> {


    ImagePresenter(Context context) {
        super(context);
    }
}
