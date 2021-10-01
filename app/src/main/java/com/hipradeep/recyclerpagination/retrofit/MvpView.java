package com.hipradeep.recyclerpagination.retrofit;

import androidx.annotation.StringRes;

/**
 * Created by Az on 29/6/18.
 */

public interface MvpView {
    void showLoading();

    void hideLoading();

    void openActivityOnTokenExpire();

    void onError(@StringRes int resId);

    void onError(String message);

    void showMessage(String message);

    void showMessage(@StringRes int resId);

    boolean isNetworkConnected();

    void hideKeyboard();

    void sendUnUsedEPin(String epinNo, String from);

    void getvehicledata(String vehiclename, String vehicleid);

    void getAccpetRejectBooking(String id, String bookid, String action, String remark, String reason);
}
