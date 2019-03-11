package com.example.android.vkgroup.data.repository;

import android.util.Log;

import com.example.android.vkgroup.data.model.GroupModel;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

public class DataSingleVkRepository implements VkRepository {
    @Inject
    VKRequest request;
    private List<GroupModel> listGroups = new ArrayList<>();
    private String vkName;
    private String vkSubscription;
    private String vkAvatar;

    public List<GroupModel> getListGroups() {
        return listGroups;
    }

    public Single<List<GroupModel>> getListGroupsSingle() {
        return Single.create(subscriber -> {
            request.executeWithListener(new VKRequest.VKRequestListener() {

                @Override
                public void onComplete(VKResponse response) {
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
                        subscriber.onSuccess(listGroups);
                        Log.e("vkList", listGroups.toString());
                    }
                }

                @Override
                public void onError(VKError error) {
                    super.onError(error);

                }

            });
        });
    }
}