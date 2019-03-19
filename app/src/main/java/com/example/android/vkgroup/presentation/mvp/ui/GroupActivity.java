package com.example.android.vkgroup.presentation.mvp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.android.vkgroup.data.model.GroupModel;
import com.example.android.vkgroup.presentation.adapter.GroupAdapterRv;
import com.example.android.vkgroup.presentation.app.App;
import com.example.android.vkgroup.presentation.mvp.presenter.GroupPresenter;
import com.example.android.vkgroup.R;
import com.example.android.vkgroup.presentation.mvp.view.GroupView;
import com.github.rahatarmanahmed.cpv.CircularProgressView;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTextChanged;

import static com.example.android.vkgroup.presentation.helper.AskOnline.isOnline;

public class GroupActivity extends MvpAppCompatActivity implements GroupView {
    @BindView(R.id.txt_search)
    EditText searchGroup;
    @BindView(R.id.txt_groups_no_item)
    TextView txtNoItem;
    @BindView(R.id.cpv_groups)
    CircularProgressView cpvWait;
    @BindView(R.id.recycler_groups)
    RecyclerView rvGroups;

    private GroupAdapterRv groupAdapterRv;

    @Inject
    @InjectPresenter
    GroupPresenter groupPresenter;

    @ProvidePresenter
    GroupPresenter providePresenter() {
        return groupPresenter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        App.getComponent().inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);
        ButterKnife.bind(this);

        if (isOnline(this)) {
            VKSdk.login(this, VKScope.GROUPS);
        }

        groupAdapterRv = new GroupAdapterRv();
        rvGroups.setAdapter(groupAdapterRv);
        rvGroups.setLayoutManager(new LinearLayoutManager(getApplicationContext(), OrientationHelper.VERTICAL, false));
        rvGroups.setHasFixedSize(true);
        groupPresenter.favoriteListener(groupAdapterRv);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        groupPresenter.loginVk(requestCode, resultCode, data);
    }

    @OnTextChanged(R.id.txt_search)
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        groupAdapterRv.filter(s.toString());
    }

    @Override
    public void startLoading() {
        txtNoItem.setVisibility(View.GONE);
        rvGroups.setVisibility(View.GONE);
        cpvWait.setVisibility(View.VISIBLE);
    }

    @Override
    public void endLoading() {
        cpvWait.setVisibility(View.GONE);
    }

    @Override
    public void showError(int textResource) {
        txtNoItem.setText(getString(textResource));
    }

    @Override
    public void setupEmptyList() {
        rvGroups.setVisibility(View.GONE);
        txtNoItem.setVisibility(View.VISIBLE);
    }

    @Override
    public void setupGroupsList(List<GroupModel> groupsList) {
        rvGroups.setVisibility(View.VISIBLE);
        txtNoItem.setVisibility(View.GONE);
        groupAdapterRv.setupGroups(groupsList);
    }

    public void onClickDone(View view) {
        startActivity(new Intent(GroupActivity.this, FavoriteActivity.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
        groupPresenter.onInitGroupsDb();
    }

}
