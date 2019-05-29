
package com.example.examApp.ui.base;

public interface MvpPresenter<V extends MvpView> {

    void onAttach(V mvpView);

}
