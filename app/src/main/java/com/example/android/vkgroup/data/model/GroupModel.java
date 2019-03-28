package com.example.android.vkgroup.data.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(exclude = {"id", "subscribers", "avatar", "isFavorite"})
@Entity(tableName = "dbGroups")
public class GroupModel {

    @PrimaryKey(autoGenerate = true)
    private long id;
    private String name;
    private String subscribers;
    private String avatar;
    private Boolean isFavorite;

    public GroupModel(String name, String subscribers, String avatar, Boolean isFavorite) {
        this.name = name;
        this.subscribers = subscribers;
        this.avatar = avatar;
        this.isFavorite = isFavorite;
    }
}