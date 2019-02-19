package com.example.android.vkgroup.providers;

import android.content.Context;
import android.util.Log;

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

import io.reactivex.BackpressureStrategy;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


import static com.example.android.vkgroup.models.AppDatabase.getDatabase;
import static com.example.android.vkgroup.models.AppDatabase.getINSTANCE;

import static com.example.android.vkgroup.models.AppDatabase.listDb;
import static java.lang.Math.log;

public class GroupDbProvider {

    private GroupPresenter dbPresenter;
    List<GroupModel> listGroups = new ArrayList<>();
    public static final String TAG = "JSON";
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
                Log.e(TAG, String.valueOf(vkName+" || "+vkSubscription+ " || "+vkAvatar));

            }

              /*  VKList list = (VKList) response.parsedModel;
                 Log.e(TAG, String.valueOf(list));*/
            //  gPesenter.groupsLoaded(listGroups);

          //  listDb(listGroups);



        }
            @Override
            public void onError (VKError error){
                super.onError(error);
                dbPresenter.showError(R.string.show_error_loading);
            }
});

}

/*public Disposable mCompletable = Completable
        .fromAction(new Action() {
    @Override
    public void run() throws Exception {
        listDb(listGroups);
    }
}).subscribeOn(Schedulers.newThread())
        .subscribe();*/

    Callable<Void> clb = new Callable<Void>() {
        @Override
        public Void call() throws Exception {
            listDb(listGroups);
            return null;
        }
    };

    public Disposable mCompletable = Completable.fromCallable(clb)
            .subscribeOn(Schedulers.newThread())
            .subscribe();

/*public Disposable mGroupModelObservable = Observable.fromIterable(listGroups)
        .subscribeOn(Schedulers.io())

        .subscribe(new Consumer<GroupModel>() {
            @Override
            public void accept(GroupModel groupModel) throws Exception {
                getINSTANCE().getModelDao().insertAll(groupModel);
            }
        });*/





}