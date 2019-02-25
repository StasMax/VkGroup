package com.example.android.vkgroup.activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.android.vkgroup.models.AppDatabase;
import com.example.android.vkgroup.presenters.LoginPresenter;
import com.example.android.vkgroup.R;
import com.example.android.vkgroup.views.LoginView;
import com.github.rahatarmanahmed.cpv.CircularProgressView;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;

import static com.example.android.vkgroup.models.AppDatabase.getDatabase;

public class StartActivity extends MvpAppCompatActivity implements LoginView {
TextView mTextHello;
Button mButEnter;
CircularProgressView mCpvWait;
    AppDatabase db;



    @InjectPresenter
    LoginPresenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        getDatabase(this);
        mTextHello = (TextView)findViewById(R.id.privetstvie);
        mButEnter = (Button)findViewById(R.id.enter);
        mCpvWait = (CircularProgressView)findViewById(R.id.cpv_login);
        mButEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  loginPresenter.login(true);
                if (isOnline(StartActivity.this)){
                VKSdk.login(StartActivity.this, VKScope.GROUPS);
                }else openGroups();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        // super.onActivityResult(requestCode, resultCode, data);
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
        Intent intent = new Intent(getApplicationContext(), GroupActivity.class);
        startActivity(intent);

    }

    public static boolean isOnline(Context context)
    {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting())
        {
            return true;
        }
        return false;
    }

}
