package com.example.android.vkgroup.data.repository;

import com.example.android.vkgroup.data.model.GroupModel;
import com.example.android.vkgroup.presentation.app.App;
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

import javax.inject.Inject;

import io.reactivex.Single;

public class DataSingleVkRepository implements VkRepository {

    VKRequest request = VKApi.groups().get(VKParameters.from(VKApiConst.FIELDS, "members_count", VKApiConst.EXTENDED, 1));
    private List<GroupModel> listGroups = new ArrayList<>();
    private String vkName;
    private String vkSubscription;
    private String vkAvatar;

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
                    }
                    subscriber.onSuccess(listGroups);
                }

                @Override
                public void onError(VKError error) {
                    super.onError(error);
                }

            });
        });
    }
}