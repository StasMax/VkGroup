package com.example.android.vkgroup.activities;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.android.vkgroup.Models.GroupModel;
import com.example.android.vkgroup.Presenters.GroupPresenter;
import com.example.android.vkgroup.R;
import com.example.android.vkgroup.adapters.GroupAdapter;
import com.example.android.vkgroup.views.GroupView;
import com.github.rahatarmanahmed.cpv.CircularProgressView;

import java.util.List;

public class GroupActivity extends MvpAppCompatActivity implements GroupView {
    EditText searchGroup;
    TextView mTxtNoItem;
    CircularProgressView mCpvWait;
    RecyclerView mRvGroups;
    GroupAdapter mAdapter;

    @InjectPresenter
    GroupPresenter groupPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);
        searchGroup = (EditText)findViewById(R.id.txt_search);
        mTxtNoItem = (TextView)findViewById(R.id.txt_groups_no_item);
        mCpvWait = (CircularProgressView)findViewById(R.id.cpv_groups);
        mRvGroups = (RecyclerView)findViewById(R.id.recycler_groups);

        groupPresenter.loadGroups();
        mAdapter = new GroupAdapter();

        // Назначаем адаптер для RecyclerView
        mRvGroups.setAdapter(mAdapter);

        //Назначаем параметры для RecyclerView
        mRvGroups.setLayoutManager(new LinearLayoutManager(getApplicationContext(), OrientationHelper.VERTICAL, false));
        mRvGroups.setHasFixedSize(true);


        searchGroup.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            mAdapter.filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

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

        //Передаем нужный параметр из презентера
        mAdapter.setupGroups(groupsList);
    }
}
