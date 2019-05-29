package com.example.examApp.ui.image;

import android.content.Context;
import com.example.examApp.ui.base.BasePresenter;

class ImagePresenter<V extends ImageMvpView> extends BasePresenter<V> implements ImageMvpPresenter<V> {


    ImagePresenter(Context context) {
        super(context);
    }
}
