package com.example.android.vkgroup.providers;


import android.os.Handler;
import com.example.android.vkgroup.models.AppDatabase;
import com.example.android.vkgroup.models.GroupModel;
import com.example.android.vkgroup.presenters.GroupPresenter;

import java.util.ArrayList;
import java.util.List;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

import static com.example.android.vkgroup.models.AppDatabase.getINSTANCE;
import static com.example.android.vkgroup.models.AppDatabase.loadLstDb;


public class GroupProvider {
   private GroupPresenter gPesenter;
   public static final String TAG = "JSON";
    List<GroupModel>testListGroups = new ArrayList<>();

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
                    testListGroups.add(group1);
                    testListGroups.add(group2);
                    testListGroups.add(group3);
                    testListGroups.add(group4);
                    testListGroups.add(group5);
                    gPesenter.groupsLoaded(testListGroups);
                }
            }
        }, 2000);
    }

    public void loadGroups(){

                         getINSTANCE().getModelDao().getAll()
                        .observeOn(AndroidSchedulers.mainThread())

                        .subscribe(new Consumer<List<GroupModel>>() {
                            @Override
                            public void accept(List<GroupModel> loadGroupList) throws Exception {
                                gPesenter.groupsLoaded(loadGroupList);
                            }
                        });

      //  gPesenter.groupsLoaded(loadLstDb());


    }
}
