
package com.example.examapp.ui.base;

public interface MvpPresenter<V extends MvpView> {

    void onAttach(V mvpView);

}
