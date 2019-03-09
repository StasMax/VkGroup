package com.example.android.vkgroup.data.provider;


import com.example.android.vkgroup.presentation.app.App;
import com.example.android.vkgroup.data.model.GroupModel;
import com.example.android.vkgroup.data.model.ModelDao;
import com.example.android.vkgroup.data.model.ModelRepository;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class GroupDbProvider {




    public GroupDbProvider() {
        App.getComponent().inject(this);
    }

    public void loadGroupToDb() {
        listGroups.clear();


        Callable<Void> cdb = () -> {
            queryDbList = modelRepository.loadLstDb();
            if (queryDbList != null) {
                modelRepository.deleteAllDb(queryDbList);
            }
            return null;
        };
        Completable.fromCallable(cdb)
                .subscribeOn(Schedulers.newThread())
                .subscribe();


    }
}