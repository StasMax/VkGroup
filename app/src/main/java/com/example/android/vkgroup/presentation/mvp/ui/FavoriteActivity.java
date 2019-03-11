package com.example.android.vkgroup.presentation.mvp.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.android.vkgroup.R;
import com.example.android.vkgroup.presentation.adapter.GroupAdapterRv;
import com.example.android.vkgroup.presentation.app.App;
import com.example.android.vkgroup.presentation.mvp.presenter.FavoritePresenter;
import com.example.android.vkgroup.presentation.mvp.view.FavoriteView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavoriteActivity extends MvpAppCompatActivity implements FavoriteView {
    @BindView(R.id.txt_groups_no_item_favorite)
    TextView textViewNoFi;
    @BindView(R.id.favorite_recycler_view)
    RecyclerView recyclerView;
    @Inject
    GroupAdapterRv groupAdapterRv;

    @InjectPresenter
    FavoritePresenter favoritePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        ButterKnife.bind(this);
        App.getComponent().inject(this);
        favoritePresenter.loadGroups();
        recyclerView.setAdapter(groupAdapterRv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), OrientationHelper.VERTICAL, false));
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
