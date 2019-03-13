package com.example.android.vkgroup.di.module;

import com.example.android.vkgroup.data.model.ModelRepository;
import com.example.android.vkgroup.data.repository.DataSingleVkRepository;
import com.example.android.vkgroup.data.repository.VkRepository;
import com.example.android.vkgroup.domain.interactor.GroupDomainInteractor;
import com.example.android.vkgroup.domain.interactor.GroupInteractor;
import com.example.android.vkgroup.di.scope.ApplicationScope;
import com.example.android.vkgroup.presentation.mvp.presenter.GroupPresenter;
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
    GroupPresenter dbPresenter(GroupInteractor groupInteractor) {
        return new GroupPresenter(groupInteractor);
    }

    @ApplicationScope
    @Provides
    VKRequest request(){return VKApi.groups().get(VKParameters.from(VKApiConst.FIELDS, "members_count", VKApiConst.EXTENDED, 1));}

    @ApplicationScope
    @Provides
    VkRepository vkRepository(){return new DataSingleVkRepository();
    }

    @ApplicationScope
    @Provides
    GroupInteractor groupInteractor(VkRepository vkRepository, ModelRepository modelRepository){return new GroupDomainInteractor(vkRepository, modelRepository);
    }
}
