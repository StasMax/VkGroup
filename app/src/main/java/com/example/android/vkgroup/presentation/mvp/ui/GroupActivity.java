package com.example.android.vkgroup.presentation.mvp.ui;

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
import com.example.android.vkgroup.presentation.adapter.GroupAdapterRv;
import com.example.android.vkgroup.presentation.app.App;
import com.example.android.vkgroup.presentation.mvp.presenter.GroupPresenter;
import com.example.android.vkgroup.R;
import com.example.android.vkgroup.presentation.mvp.view.GroupView;
import com.github.rahatarmanahmed.cpv.CircularProgressView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTextChanged;

public class GroupActivity extends MvpAppCompatActivity implements GroupView {
    @BindView(R.id.txt_search)
    EditText searchGroup;
    @BindView(R.id.txt_groups_no_item)
    TextView mTxtNoItem;
    @BindView(R.id.cpv_groups)
    CircularProgressView mCpvWait;
    @BindView(R.id.recycler_groups)
    RecyclerView rvGroups;
    @Inject
    GroupAdapterRv groupAdapterRv;
    @InjectPresenter
    GroupPresenter groupPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);
        ButterKnife.bind(this);
        App.getComponent().inject(this);
        groupPresenter.loadGroupsVk();
        rvGroups.setAdapter(groupAdapterRv);
        rvGroups.setLayoutManager(new LinearLayoutManager(getApplicationContext(), OrientationHelper.VERTICAL, false));
        rvGroups.setHasFixedSize(true);
    }

    @OnTextChanged(R.id.txt_search)
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        groupAdapterRv.filter(s.toString());
    }

    @Override
    public void startLoading() {
        mTxtNoItem.setVisibility(View.GONE);
        rvGroups.setVisibility(View.GONE);
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
        rvGroups.setVisibility(View.GONE);
        mTxtNoItem.setVisibility(View.VISIBLE);
    }

    @Override
    public void setupGroupsList() {
        rvGroups.setVisibility(View.VISIBLE);
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
