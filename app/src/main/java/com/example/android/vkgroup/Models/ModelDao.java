package com.example.android.vkgroup.Models;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

;import io.reactivex.Flowable;

@Dao
public interface ModelDao {
    @Query("SELECT * FROM dbGroups")
    Flowable<List<GroupModel>> getAll();

    @Query("SELECT * FROM dbGroups WHERE id = :id")
    GroupModel getById(long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertFavorite(GroupModel groupModel);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertList(List<GroupModel> groupModelList);

    @Update
    void update(List<GroupModel> groupModelList);

    @Delete
    void deleteFavorite(List<GroupModel> groupModelList);
}
