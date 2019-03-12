package com.example.android.vkgroup.presentation.mvp.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.android.vkgroup.R;
import com.example.android.vkgroup.presentation.mvp.view.LoginView;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKError;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import static com.example.android.vkgroup.presentation.helper.Helper.isOnline;

@InjectViewState
public class LoginPresenter extends MvpPresenter<LoginView> {

    public void loginVk(int requestCode, int resultCode, Intent data) {
        VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {
                getViewState().openGroups();
            }

            @Override
            public void onError(VKError error) {
                getViewState().showError(R.string.error_login);
            }
        });
    }

    public void clickEnter(Context context) {
        if (isOnline(context)) {
            VKSdk.login((Activity) context, VKScope.GROUPS);
        } else
            getViewState().openGroups();
    }
}
