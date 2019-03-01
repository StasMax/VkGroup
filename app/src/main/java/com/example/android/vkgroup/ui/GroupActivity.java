package com.example.android.vkgroup.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.android.vkgroup.adapter.TextWatcherAdapter;
import com.example.android.vkgroup.model.GroupModel;
import com.example.android.vkgroup.presenter.GroupPresenter;
import com.example.android.vkgroup.R;
import com.example.android.vkgroup.adapter.GroupAdapter;
import com.example.android.vkgroup.view.GroupView;
import com.github.rahatarmanahmed.cpv.CircularProgressView;

import java.util.List;

public class GroupActivity extends MvpAppCompatActivity implements GroupView {
    EditText searchGroup;
    TextView mTxtNoItem;
    CircularProgressView mCpvWait;
    RecyclerView mRvGroups;
    GroupAdapter mAdapter = new GroupAdapter();

    @InjectPresenter
    GroupPresenter groupPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);
        searchGroup = findViewById(R.id.txt_search);
        mTxtNoItem = findViewById(R.id.txt_groups_no_item);
        mCpvWait = findViewById(R.id.cpv_groups);
        mRvGroups = findViewById(R.id.recycler_groups);

        groupPresenter.loadGroups();

        mRvGroups.setAdapter(mAdapter);
        mRvGroups.setLayoutManager(new LinearLayoutManager(getApplicationContext(), OrientationHelper.VERTICAL, false));
        mRvGroups.setHasFixedSize(true);

        searchGroup.addTextChangedListener(new TextWatcherAdapter() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mAdapter.filter(s.toString());
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
    public void setupGroupsList(List<GroupModel> groupsList) {
        mRvGroups.setVisibility(View.VISIBLE);
        mTxtNoItem.setVisibility(View.GONE);
        mAdapter.setupGroups(groupsList);
    }

    public void onClickDone(View view) {
        startActivity(new Intent(GroupActivity.this, FavoriteActivity.class));
    }
}
