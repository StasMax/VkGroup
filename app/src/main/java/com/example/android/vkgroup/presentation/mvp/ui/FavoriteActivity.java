package com.example.android.vkgroup.presentation.mvp.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.android.vkgroup.R;
import com.example.android.vkgroup.data.model.GroupModel;
import com.example.android.vkgroup.presentation.adapter.GroupAdapterRv;
import com.example.android.vkgroup.presentation.app.App;
import com.example.android.vkgroup.presentation.mvp.presenter.FavoritePresenter;
import com.example.android.vkgroup.presentation.mvp.view.FavoriteView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavoriteActivity extends MvpAppCompatActivity implements FavoriteView {
    @BindView(R.id.txt_groups_no_item_favorite)
    TextView textViewNoFi;
    @BindView(R.id.favorite_recycler_view)
    RecyclerView recyclerView;

    GroupAdapterRv groupAdapterRv;

    @Inject
    @InjectPresenter
    FavoritePresenter favoritePresenter;

    @ProvidePresenter
    FavoritePresenter providePresenter() {
        return favoritePresenter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        App.getComponent().inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        ButterKnife.bind(this);

        favoritePresenter.onInitFavoriteGroups();

        groupAdapterRv = new GroupAdapterRv();
        recyclerView.setAdapter(groupAdapterRv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), OrientationHelper.VERTICAL, false));
        favoriteListener(groupAdapterRv);
    }

    public void favoriteListener(GroupAdapterRv groupAdapterRv) {
        groupAdapterRv.setListener((groupModel, isChecked) -> {
            favoritePresenter.onSetFavorite(groupModel, isChecked);
        });
    }

    @Override
    public void setupEmptyList() {
        textViewNoFi.setVisibility(View.VISIBLE);
    }

    @Override
    public void setupGroupsList(List<GroupModel> groupModelFavoriteList) {
        textViewNoFi.setVisibility(View.GONE);
        groupAdapterRv.setupFavoriteGroups(groupModelFavoriteList);
    }
}
