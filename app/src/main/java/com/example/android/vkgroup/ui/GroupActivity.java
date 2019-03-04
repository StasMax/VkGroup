package com.example.android.vkgroup.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.android.vkgroup.adapter.GroupAdapterRv;
import com.example.android.vkgroup.adapter.TextWatcherAdapter;
import com.example.android.vkgroup.app.App;
import com.example.android.vkgroup.presenter.GroupPresenter;
import com.example.android.vkgroup.R;
import com.example.android.vkgroup.view.GroupView;
import com.github.rahatarmanahmed.cpv.CircularProgressView;

import javax.inject.Inject;

public class GroupActivity extends MvpAppCompatActivity implements GroupView {
    EditText searchGroup;
    TextView mTxtNoItem;
    CircularProgressView mCpvWait;
    RecyclerView mRvGroups;
       @Inject
    GroupAdapterRv groupAdapterRv;

    @InjectPresenter
    GroupPresenter groupPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);
        App.getComponent().inject(this);
        searchGroup = findViewById(R.id.txt_search);
        mTxtNoItem = findViewById(R.id.txt_groups_no_item);
        mCpvWait = findViewById(R.id.cpv_groups);
        mRvGroups = findViewById(R.id.recycler_groups);

        groupPresenter.loadGroupsVk();

        mRvGroups.setAdapter(groupAdapterRv);
        mRvGroups.setLayoutManager(new LinearLayoutManager(getApplicationContext(), OrientationHelper.VERTICAL, false));
        mRvGroups.setHasFixedSize(true);

        searchGroup.addTextChangedListener(new TextWatcherAdapter() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                groupAdapterRv.filter(s.toString());
            }
        });
    }

    @Override
    public void startLoading() {
        mTxtNoItem.setVisibility(View.GONE);
        mRvGroups.setVisibility(View.GONE);
        mCpvWait.setVisibility(View.VISIBLE);
    }

    @Override
    public void endLoading() {
        mCpvWait.setVisibility(View.GONE);
    }

    @Override
    public void showError(int textResource) {
        mTxtNoItem.setText(getString(textResource));
    }

    @Override
    public void setupEmptyList() {
        mRvGroups.setVisibility(View.GONE);
        mTxtNoItem.setVisibility(View.VISIBLE);
    }

    @Override
    public void setupGroupsList() {
        mRvGroups.setVisibility(View.VISIBLE);
        mTxtNoItem.setVisibility(View.GONE);
    }

    public void onClickDone(View view) {
        startActivity(new Intent(GroupActivity.this, FavoriteActivity.class));
    }

    @Override
    protected void onStart() {
        super.onStart();
        groupPresenter.loadGroupsFromDb();
    }


}
