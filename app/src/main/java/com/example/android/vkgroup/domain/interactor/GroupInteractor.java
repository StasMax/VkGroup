package com.example.android.vkgroup.domain.interactor;

import com.example.android.vkgroup.data.model.GroupModel;

public interface GroupInteractor {
    void getFavorite();
    void getAllListGroupsVk();
    void getGroupsListFromDb();
    void updateFavorite(GroupModel groupModel);
}
