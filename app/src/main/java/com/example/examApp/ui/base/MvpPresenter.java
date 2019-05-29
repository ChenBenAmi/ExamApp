
package com.example.examApp.ui.base;
/**
 * @author Chen.
 * @version 1 at 30/5/2019.
 *Base Presenter interface
 */

public interface MvpPresenter<V extends MvpView> {

    void onAttach(V mvpView);

}
