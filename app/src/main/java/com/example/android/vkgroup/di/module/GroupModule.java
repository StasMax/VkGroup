package com.example.android.vkgroup.di.module;

import com.example.android.vkgroup.data.model.IModelRepository;
import com.example.android.vkgroup.data.repository.DataSingleVkRepository;
import com.example.android.vkgroup.data.repository.VkRepository;
import com.example.android.vkgroup.domain.interactor.GroupInteractorImpl;
import com.example.android.vkgroup.domain.interactor.IGroupInteractor;
import com.example.android.vkgroup.di.scope.ApplicationScope;

import dagger.Module;
import dagger.Provides;

@Module
public class GroupModule {

    @ApplicationScope
    @Provides
    VkRepository vkRepository() {
        return new DataSingleVkRepository();
    }

    @ApplicationScope
    @Provides
    IGroupInteractor groupInteractor(VkRepository vkRepository, IModelRepository modelRepository) {
        return new GroupInteractorImpl(vkRepository, modelRepository);
    }
}
