package com.example.android.vkgroup.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.android.vkgroup.R;
import com.example.android.vkgroup.views.LoginView;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKError;

import android.content.Intent;
import android.os.Handler;

@InjectViewState
public class LoginPresenter extends MvpPresenter<LoginView> {

    public void login(final Boolean isSuccess) {
        getViewState().startLoading();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getViewState().endLoading();

            }
        }, 500);
        if (isSuccess) getViewState().openGroups();
        else getViewState().showError(R.string.error_login);
    }


    public void loginVk(int requestCode, int resultCode, Intent data) {
        if (!VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {

            @Override
            public void onResult(VKAccessToken res) {
                getViewState().openGroups();
            }

            @Override
            public void onError(VKError error) {
                getViewState().showError(R.string.error_login);
            }
        })) ;
    }


}
