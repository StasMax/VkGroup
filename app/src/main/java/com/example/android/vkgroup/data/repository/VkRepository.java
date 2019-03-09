package com.example.android.vkgroup.data.repository;

import com.example.android.vkgroup.data.model.GroupModel;

import java.util.List;

public interface VkRepository {
    List<GroupModel> getListGroups();
}
