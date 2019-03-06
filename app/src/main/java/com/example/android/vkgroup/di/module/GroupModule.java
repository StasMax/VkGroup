package com.example.android.vkgroup.di.module;

import com.example.android.vkgroup.adapter.GroupAdapterRv;
import com.example.android.vkgroup.di.scope.ApplicationScope;
import com.example.android.vkgroup.presenter.GroupPresenter;
import com.example.android.vkgroup.provider.GroupDbProvider;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;

import dagger.Module;
import dagger.Provides;

@Module
public class GroupModule {
    @ApplicationScope
    @Provides
    GroupPresenter dbPresenter() {
        return new GroupPresenter();
    }

    @ApplicationScope
    @Provides
    GroupDbProvider groupDbProvider() {
        return new GroupDbProvider();
    }

    @ApplicationScope
    @Provides
    GroupAdapterRv groupAdapterRv() {
        return new GroupAdapterRv();
    }

    @ApplicationScope
    @Provides
    VKRequest request(){return VKApi.groups().get(VKParameters.from(VKApiConst.FIELDS, "members_count", VKApiConst.EXTENDED, 1));}

}
