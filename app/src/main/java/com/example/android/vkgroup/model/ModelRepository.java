package com.example.android.vkgroup.model;

import java.util.List;

public interface ModelRepository {

    void listDb(List<GroupModel> groupModelList);

    List<GroupModel> loadLstDb();

    void deleteAllDb(List<GroupModel> groupModelList);

    void updateGmList(List<GroupModel> groupModelList);

    void setFavorite(GroupModel groupModel);

    void setOutFavorite(GroupModel groupModel);
}
