package com.example.android.vkgroup.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.android.vkgroup.app.App;
import com.example.android.vkgroup.presenter.LoginPresenter;
import com.example.android.vkgroup.R;
import com.example.android.vkgroup.view.LoginView;
import com.github.rahatarmanahmed.cpv.CircularProgressView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StartActivity extends MvpAppCompatActivity implements LoginView {
    @BindView(R.id.privetstvie)
    TextView mTextHello;
    @BindView(R.id.enter)
    Button mButEnter;
    @BindView(R.id.cpv_login)
    CircularProgressView mCpvWait;
    @InjectPresenter
    LoginPresenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        ButterKnife.bind(this);
        App.getComponent().inject(this);
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
        mButEnter.setVisibility(View.GONE);
        mCpvWait.setVisibility(View.VISIBLE);
    }

    @Override
    public void endLoading() {
        mButEnter.setVisibility(View.VISIBLE);
        mCpvWait.setVisibility(View.GONE);
    }

    @Override
    public void showError(int text) {
        Toast.makeText(StartActivity.this, text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void openGroups() {
        startActivity(new Intent(getApplicationContext(), GroupActivity.class));
    }
}
