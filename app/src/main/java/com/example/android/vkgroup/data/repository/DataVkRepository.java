package com.example.android.vkgroup.data.repository;

import com.example.android.vkgroup.data.model.GroupModel;
import com.example.android.vkgroup.presentation.app.App;
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
import io.reactivex.schedulers.Schedulers;

public class DataVkRepository implements VkRepository {
    @Inject
    VKRequest request;
    private List<GroupModel> listGroups;
    private String vkName;
    private String vkSubscription;
    private String vkAvatar;

    public DataVkRepository() {
        App.getComponent().inject(this);
        listGroups = new ArrayList<>();
    }

    public List<GroupModel> getListGroups() {
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
        return listGroups;
    }
}
