package com.example.android.vkgroup.presentation.mvp.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.android.vkgroup.R;
import com.example.android.vkgroup.data.model.GroupModel;
import com.example.android.vkgroup.data.repository.DataSingleVkRepository;
import com.example.android.vkgroup.presentation.mvp.view.LoginView;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKError;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.List;

import io.reactivex.observers.DisposableSingleObserver;

import static com.example.android.vkgroup.presentation.helper.Helper.isOnline;

@InjectViewState
public class LoginPresenter extends MvpPresenter<LoginView> {
    DataSingleVkRepository mDataSingleVkRepository = new DataSingleVkRepository();

    public void loginVk(int requestCode, int resultCode, Intent data) {
        if (!VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {
                mDataSingleVkRepository.getListGroupsSingle().subscribe(new DisposableSingleObserver<List<GroupModel>>() {
                    @Override
                    public void onSuccess(List<GroupModel> groupModels) {
                        Log.e("vkList", mDataSingleVkRepository.getListGroups().toString());


                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
                getViewState().openGroups();
            }

            @Override
            public void onError(VKError error) {
                getViewState().showError(R.string.error_login);
            }
        })) ;
    }

    public void clickEnter(Context context) {
        if (isOnline(context)) {
            VKSdk.login((Activity) context, VKScope.GROUPS);
        } else
            getViewState().openGroups();
    }
}
