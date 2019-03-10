package com.example.android.vkgroup.presentation.di.module;

import com.example.android.vkgroup.data.model.ModelRepository;
import com.example.android.vkgroup.data.repository.DataVkRepository;
import com.example.android.vkgroup.data.repository.VkRepository;
import com.example.android.vkgroup.domain.interactor.GroupDomainInteractor;
import com.example.android.vkgroup.domain.interactor.GroupInteractor;
import com.example.android.vkgroup.presentation.adapter.GroupAdapterRv;
import com.example.android.vkgroup.presentation.di.scope.ApplicationScope;
import com.example.android.vkgroup.presentation.mvp.presenter.GroupPresenter;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

@Module
public class GroupModule {
    @ApplicationScope
    @Provides
    GroupPresenter dbPresenter() {
        return new GroupPresenter();
    }

    @ApplicationScope
    @Provides
    GroupAdapterRv groupAdapterRv() {
        return new GroupAdapterRv();
    }

    @ApplicationScope
    @Provides
    VKRequest request(){return VKApi.groups().get(VKParameters.from(VKApiConst.FIELDS, "members_count", VKApiConst.EXTENDED, 1));}

    @ApplicationScope
    @Provides
    VkRepository vkRepository(){return new DataVkRepository();
    }

    @ApplicationScope
    @Provides
    GroupInteractor groupInteractor(){return new GroupDomainInteractor();}

   /* @ApplicationScope
    @Provides
    GroupInteractor groupInteractor(VkRepository vkRepository, ModelRepository modelRepository){return new GroupDomainInteractor(vkRepository, modelRepository);
    }*/

    @Provides
    CompositeDisposable compositeDisposable(){return new CompositeDisposable();}
}