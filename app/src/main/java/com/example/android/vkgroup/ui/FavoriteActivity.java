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
import com.example.android.vkgroup.adapter.GroupAdapter;
import com.example.android.vkgroup.model.GroupModel;
import com.example.android.vkgroup.presenter.FavoritePresenter;
import com.example.android.vkgroup.view.FavoriteView;

import java.util.List;

public class FavoriteActivity extends MvpAppCompatActivity implements FavoriteView {
    GroupAdapter mGroupAdapter = new GroupAdapter();
    TextView textViewNoFi;
    RecyclerView recyclerView;

    @InjectPresenter
    FavoritePresenter favoritePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        textViewNoFi = (TextView) findViewById(R.id.txt_groups_no_item_favorite);

        favoritePresenter.loadGroups();

        recyclerView = (RecyclerView) findViewById(R.id.favorite_recycler_view);
        recyclerView.setAdapter(mGroupAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), OrientationHelper.VERTICAL, false));
        recyclerView.setHasFixedSize(true);
    }


    @Override
    public void setupEmptyList() {
        textViewNoFi.setVisibility(View.VISIBLE);
    }

    @Override
    public void setupGroupsList(List<GroupModel> groupsList) {
        textViewNoFi.setVisibility(View.GONE);
        mGroupAdapter.setupFavoriteGroups(groupsList);
    }
}
