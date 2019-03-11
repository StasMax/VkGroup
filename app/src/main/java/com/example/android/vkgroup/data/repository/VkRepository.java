package com.example.android.vkgroup.data.repository;

import com.example.android.vkgroup.data.model.GroupModel;

import java.util.List;

import io.reactivex.Single;

public interface VkRepository {
   // List<GroupModel> getListGroups();
    Single<List<GroupModel>> getListGroupsSingle();
}
