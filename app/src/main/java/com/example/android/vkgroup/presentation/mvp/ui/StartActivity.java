package com.example.android.vkgroup.presentation.mvp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.android.vkgroup.presentation.app.App;
import com.example.android.vkgroup.presentation.mvp.presenter.LoginPresenter;
import com.example.android.vkgroup.R;
import com.example.android.vkgroup.presentation.mvp.view.LoginView;
import com.github.rahatarmanahmed.cpv.CircularProgressView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StartActivity extends MvpAppCompatActivity implements LoginView {
    @BindView(R.id.privetstvie)
    TextView textHello;
    @BindView(R.id.enter)
    Button buttonEnter;
    @BindView(R.id.cpv_login)
    CircularProgressView cpvWait;
    @InjectPresenter
    LoginPresenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.enter)
    void onSaveClick() {
        loginPresenter.clickEnter(StartActivity.this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        loginPresenter.loginVk(requestCode, resultCode, data);
    }

    @Override
    public void startLoading() {
        buttonEnter.setVisibility(View.GONE);
        cpvWait.setVisibility(View.VISIBLE);
    }

    @Override
    public void endLoading() {
        buttonEnter.setVisibility(View.VISIBLE);
        cpvWait.setVisibility(View.GONE);
    }

    @Override
    public void showError(int text) {
        Toast.makeText(StartActivity.this, text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void openGroups() {
        startActivity(new Intent(this, GroupActivity.class));
    }
}
