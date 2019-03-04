package com.example.android.vkgroup.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.android.vkgroup.R;
import com.example.android.vkgroup.adapter.GroupAdapterRv;
import com.example.android.vkgroup.app.App;
import com.example.android.vkgroup.presenter.FavoritePresenter;
import com.example.android.vkgroup.view.FavoriteView;

import javax.inject.Inject;

public class FavoriteActivity extends MvpAppCompatActivity implements FavoriteView {

    TextView textViewNoFi;
    RecyclerView recyclerView;
    @Inject
    GroupAdapterRv groupAdapter;

    @InjectPresenter
    FavoritePresenter favoritePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        App.getComponent().inject(this);
        textViewNoFi = findViewById(R.id.txt_groups_no_item_favorite);

        favoritePresenter.loadGroups();

        recyclerView = findViewById(R.id.favorite_recycler_view);
        recyclerView.setAdapter(groupAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), OrientationHelper.VERTICAL, false));
        recyclerView.setHasFixedSize(true);
    }

    @Override
    public void setupEmptyList() {
        textViewNoFi.setVisibility(View.VISIBLE);
    }

    @Override
    public void setupGroupsList() {
        textViewNoFi.setVisibility(View.GONE);
    }
}
