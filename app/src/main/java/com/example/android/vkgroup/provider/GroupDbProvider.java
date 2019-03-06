package com.example.android.vkgroup.provider;


import com.example.android.vkgroup.app.App;
import com.example.android.vkgroup.model.GroupModel;
import com.example.android.vkgroup.model.ModelDao;
import com.example.android.vkgroup.model.ModelRepository;
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

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class GroupDbProvider {

    @Inject
    ModelRepository modelRepository;
    @Inject
    ModelDao modelDao;
    private List<GroupModel> listGroups = new ArrayList<>();
    private List<GroupModel> queryDbList;
    private List<GroupModel> queryDbListFavorite = new ArrayList<>();
    private String vkName;
    private String vkSubscription;
    private String vkAvatar;

    public GroupDbProvider() {
        App.getComponent().inject(this);
    }

    public void loadGroupToDb() {
        listGroups.clear();
        modelDao.getByFavoriteSingle(true)
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

        VKRequest request = VKApi.groups().get(VKParameters.from(VKApiConst.FIELDS, "members_count", VKApiConst.EXTENDED, 1));
        request.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                super.onComplete(response);
                JsonParser jsonParser = new JsonParser();
                JsonObject parsedJson = jsonParser.parse(response.json.toString()).getAsJsonObject();
                JsonArray jsonArray = parsedJson.get("response").getAsJsonObject().getAsJsonArray("items");
                for (JsonElement je : jsonArray) {
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

                    for (int i = 0; i < listGroups.size(); i++) {
                        for (int j = 0; j < queryDbListFavorite.size(); j++) {
                            if (queryDbListFavorite.get(j).equals(listGroups.get(i))) {
                                listGroups.get(i).setFavorite(queryDbListFavorite.get(j).getFavorite());
                            }
                        }
                    }
                }
                Callable<Void> clb = () -> {
                    modelRepository.listDb(listGroups);
                    return null;
                };
                Completable.fromCallable(clb)
                        .subscribeOn(Schedulers.newThread())
                        .subscribe();
            }

            @Override
            public void onError(VKError error) {
                super.onError(error);
            }
        });
    }
}