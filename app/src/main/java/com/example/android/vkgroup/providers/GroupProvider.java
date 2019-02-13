package com.example.android.vkgroup.providers;

import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.example.android.vkgroup.Models.GroupModel;
import com.example.android.vkgroup.Presenters.GroupPresenter;
import com.example.android.vkgroup.R;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKBatchRequest;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.methods.VKApiGroups;
import com.vk.sdk.api.model.VKList;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GroupProvider {
   private GroupPresenter gPesenter;
   public static final String TAG = "JSON";
    List<GroupModel>listGroups = new ArrayList<>();
    private String vkName;
   private String vkSubscription;
   private String vkAvatar;


    public GroupProvider(GroupPresenter gPesenter) {
        this.gPesenter = gPesenter;
    }

    public void testLoadGroups(final Boolean hasGroups){


Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                if (hasGroups){
                   GroupModel group1 = new GroupModel("Бизнес литература", "34890", "http://img0.liveinternet.ru/images/attach/d/1/130/308/130308038_literatura.jpg", true);
                    GroupModel group2 = new GroupModel("Интернет", "34890", "http://ai-news.ru/images/posts/pimg2/pimg2-1018286.jpg", false);
                    GroupModel group3 = new GroupModel("Весёлые котики", "562891", "https://www.fiesta.city/uploads/slider_image/image/77679/share_thumb_I3GbJ62qsao.jpg",false);
                    GroupModel group4 = new GroupModel("Автомобили", "242870", "http://www.anypics.ru/mini/201210/16879.jpg", true);
                    GroupModel group5 = new GroupModel("Цитаты", "27850", "http://nastroenee.ru/wp-content/uploads/2018/10/60-3.jpg", true);
                    listGroups.add(group1);
                    listGroups.add(group2);
                    listGroups.add(group3);
                    listGroups.add(group4);
                    listGroups.add(group5);
                    gPesenter.groupsLoaded(listGroups);
                }

            }
        }, 2000);

    }

    public void loadGroups(){

          listGroups.clear();
         VKRequest request = VKApi.groups().get(VKParameters.from(VKApiConst.COUNT, 75, VKApiConst.FIELDS, "members_count", VKApiConst.EXTENDED, 1 ));


        request.executeWithListener(new VKRequest.VKRequestListener() {

            @Override
            public void onComplete(VKResponse response) {
                super.onComplete(response);
                JsonParser jsonParser = new JsonParser();
                JsonObject parsedJson = jsonParser.parse(response.json.toString()).getAsJsonObject();
               JsonArray jsonArray = parsedJson.get("response").getAsJsonObject().getAsJsonArray("items");

                for (JsonElement je:jsonArray
                     ) {
                    vkName = je.getAsJsonObject().get("name").toString();
                    if (je.getAsJsonObject().get("members_count")!=null){
                    vkSubscription = je.getAsJsonObject().get("members_count").toString();}

                    vkAvatar = je.getAsJsonObject().get("photo_100").getAsString();

                   listGroups.add(new GroupModel(vkName, vkSubscription, vkAvatar, false));

                    }

              /*  VKList list = (VKList) response.parsedModel;
                 Log.e(TAG, String.valueOf(list));*/
                  gPesenter.groupsLoaded(listGroups);
            }

            @Override
            public void onError(VKError error) {
                super.onError(error);
                gPesenter.showError(R.string.show_error_loading);
            }
        });


    }
}
