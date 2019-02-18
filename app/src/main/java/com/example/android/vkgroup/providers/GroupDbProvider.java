package com.example.android.vkgroup.providers;

import com.example.android.vkgroup.models.AppDatabase;
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
import io.reactivex.functions.Action;

import static com.example.android.vkgroup.models.AppDatabase.listDb;

public class GroupDbProvider {

    private GroupPresenter dbPresenter;
    List<GroupModel> listGroups = new ArrayList<>();

    private String vkName;
    private String vkSubscription;
    private String vkAvatar;

    public GroupDbProvider(GroupPresenter dbPresenter) {
        dbPresenter = dbPresenter;
    }

public void loadGroupToDb(){

    VKRequest request = VKApi.groups().get(VKParameters.from(VKApiConst.FIELDS, "members_count", VKApiConst.EXTENDED, 1 ));

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
                vkName = je.getAsJsonObject().get("name").toString();}

                if (je.getAsJsonObject().get("members_count") != null) {
                    vkSubscription = je.getAsJsonObject().get("members_count").toString();
                }
                if (je.getAsJsonObject().get("photo_100") != null) {
                vkAvatar = je.getAsJsonObject().get("photo_100").getAsString();}

                listGroups.add(new GroupModel(vkName, vkSubscription, vkAvatar, false));

            }

              /*  VKList list = (VKList) response.parsedModel;
                 Log.e(TAG, String.valueOf(list));*/
            //  gPesenter.groupsLoaded(listGroups);

            listDb(listGroups);

        /*    Callable<Void> clb = new Callable<Void>() {
                @Override
                public Void call() throws Exception {
                    listDb(listGroups);
                    return null;
                }
            };

            Completable.fromCallable(clb).subscribe();*/



          /*  Completable.fromAction(new Action() {
                @Override
                public void run() throws Exception {
                    listDb(listGroups);
                }
            }).subscribe();*/


        }
            @Override
            public void onError (VKError error){
                super.onError(error);
                dbPresenter.showError(R.string.show_error_loading);
            }
});

}
}