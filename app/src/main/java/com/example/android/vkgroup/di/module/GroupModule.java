package com.example.android.vkgroup.di.module;

import com.example.android.vkgroup.data.model.ModelRepository;
import com.example.android.vkgroup.data.repository.DataSingleVkRepository;
import com.example.android.vkgroup.data.repository.VkRepository;
import com.example.android.vkgroup.domain.interactor.GroupDomainInteractor;
import com.example.android.vkgroup.domain.interactor.GroupInteractor;
import com.example.android.vkgroup.di.scope.ApplicationScope;

import dagger.Module;
import dagger.Provides;

@Module
public class GroupModule {

    @ApplicationScope
    @Provides
    VkRepository vkRepository(){return new DataSingleVkRepository();
    }

    @ApplicationScope
    @Provides
    GroupInteractor groupInteractor(VkRepository vkRepository, ModelRepository modelRepository){return new GroupDomainInteractor(vkRepository, modelRepository);
    }
}
