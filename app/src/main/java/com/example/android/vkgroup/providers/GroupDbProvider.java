package com.example.android.vkgroup.providers;


import android.util.Log;

import com.example.android.vkgroup.models.GroupModel;
import com.example.android.vkgroup.presenters.GroupPresenter;
import com.example.android.vkgroup.R;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

import static com.example.android.vkgroup.models.AppDatabase.deleteAllDb;
import static com.example.android.vkgroup.models.AppDatabase.getINSTANCE;
import static com.example.android.vkgroup.models.AppDatabase.listDb;
import static com.example.android.vkgroup.models.AppDatabase.loadLstDb;

public class GroupDbProvider {

    private GroupPresenter dbPresenter;
    List<GroupModel> listGroups = new ArrayList<>();
    List<GroupModel> queryDbList;
    List<GroupModel> queryDbListFavorite = new ArrayList<>();
    public static final String TAG = "JSON";
    private String vkName;
    private String vkSubscription;
    private String vkAvatar;


    public GroupDbProvider(GroupPresenter dbPresenter) {
        dbPresenter = dbPresenter;
    }

    public void loadGroupToDb() {

        listGroups.clear();

        getINSTANCE().getModelDao().getByFavorite1(true)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<List<GroupModel>>() {
                    @Override
                    public void onSuccess(List<GroupModel> groupModelList) {
                        queryDbListFavorite.addAll(groupModelList);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }
                });


        Callable<Void> cdb = new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                queryDbList = loadLstDb();
                if (queryDbList != null) {
                    deleteAllDb(queryDbList);
                }
                return null;
            }
        };

        Disposable completable = Completable.fromCallable(cdb)
                .subscribeOn(Schedulers.newThread())
                .subscribe();

        VKRequest request = VKApi.groups().get(VKParameters.from(VKApiConst.FIELDS, "members_count", VKApiConst.EXTENDED, 1));
        request.executeWithListener(new VKRequest.VKRequestListener() {

            @Override
            public void onComplete(VKResponse response) {
                super.onComplete(response);
                JsonParser jsonParser = new JsonParser();
                JsonObject parsedJson = jsonParser.parse(response.json.toString()).getAsJsonObject();
                JsonArray jsonArray = parsedJson.get("response").getAsJsonObject().getAsJsonArray("items");

                for (JsonElement je : jsonArray
                ) {

                    if (je.getAsJsonObject().get("name") != null) {
                        vkName = je.getAsJsonObject().get("name").getAsString();
                    }

                    if (je.getAsJsonObject().get("members_count") != null) {
                        vkSubscription = je.getAsJsonObject().get("members_count").getAsString();
                    }
                    if (je.getAsJsonObject().get("photo_100") != null) {
                        vkAvatar = je.getAsJsonObject().get("photo_100").getAsString();
                    }

                    listGroups.add(new GroupModel(vkName, vkSubscription, vkAvatar, false));
                    //  Log.e(TAG, String.valueOf(vkName+" || "+vkSubscription+ " || "+vkAvatar));
                    Log.e(TAG, String.valueOf(queryDbListFavorite));


                    for (int i = 0; i < listGroups.size(); i++) {
                        for (int j = 0; j < queryDbListFavorite.size(); j++) {

                            Log.e(TAG, queryDbListFavorite.get(j).getFavorite().toString());

                            if (queryDbListFavorite.get(j).equals(listGroups.get(i))) {
                                listGroups.get(i).setFavorite(queryDbListFavorite.get(j).getFavorite());

                            }
                        }
                    }

                }


                Callable<Void> clb = new Callable<Void>() {
                    @Override
                    public Void call() throws Exception {
                        listDb(listGroups);
                        //  updateGmList(queryDbListFavorite);
                        return null;
                    }
                };

                Disposable mCompletable = Completable.fromCallable(clb)
                        .subscribeOn(Schedulers.newThread())
                        .subscribe();
            }

            @Override
            public void onError(VKError error) {
                super.onError(error);
                dbPresenter.showError(R.string.show_error_loading);
            }
        });

    }

}