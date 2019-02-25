package com.example.android.vkgroup.providers;


import android.os.Handler;

import com.example.android.vkgroup.models.AppDatabase;
import com.example.android.vkgroup.models.GroupModel;
import com.example.android.vkgroup.presenters.FavoritePresenter;
import com.example.android.vkgroup.presenters.GroupPresenter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

import static com.example.android.vkgroup.models.AppDatabase.getINSTANCE;
import static com.example.android.vkgroup.models.AppDatabase.loadLstDb;


public class GroupProvider {
    private GroupPresenter gPesenter;
    private FavoritePresenter fPesenter;
    List<GroupModel> testListGroups = new ArrayList<>();


    public GroupProvider(GroupPresenter gPesenter) {
        this.gPesenter = gPesenter;
    }

    public GroupProvider(FavoritePresenter fPesenter) {
        this.fPesenter = fPesenter;
    }

    public void testLoadGroups(final Boolean hasGroups) {

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (hasGroups) {
                    testListGroups.add(new GroupModel("Бизнес литература", "34890", "http://img0.liveinternet.ru/images/attach/d/1/130/308/130308038_literatura.jpg", true));
                    testListGroups.add(new GroupModel("Интернет", "34890", "http://ai-news.ru/images/posts/pimg2/pimg2-1018286.jpg", false));
                    testListGroups.add(new GroupModel("Весёлые котики", "562891", "https://www.fiesta.city/uploads/slider_image/image/77679/share_thumb_I3GbJ62qsao.jpg", false));
                    testListGroups.add(new GroupModel("Автомобили", "242870", "http://www.anypics.ru/mini/201210/16879.jpg", true));
                    testListGroups.add(new GroupModel("Цитаты", "27850", "http://nastroenee.ru/wp-content/uploads/2018/10/60-3.jpg", true));
                    gPesenter.groupsLoaded(testListGroups);
                }
            }
        }, 2000);
    }

    public void loadGroups() {

        getINSTANCE().getModelDao().getAll()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<GroupModel>>() {
                    @Override
                    public void accept(List<GroupModel> loadGroupList) throws Exception {
                        gPesenter.groupsLoaded(loadGroupList);
                    }
                });

    }

    public void loadFavoriteGroups() {
        getINSTANCE().getModelDao().getByFavorite(true)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<GroupModel>>() {
                    @Override
                    public void accept(List<GroupModel> loadGroupList) throws Exception {
                        fPesenter.groupsFavoriteLoaded(loadGroupList);
                    }
                });
    }
}
