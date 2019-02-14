package com.example.android.vkgroup.Models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "dbGroups")
public class GroupModel {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String name;
    private String subscribers;
    private  String avatar;
    private Boolean isFavorite;

    public GroupModel(String name, String subscribers, String avatar, Boolean isFavorite) {
        this.name = name;
        this.subscribers = subscribers;
        this.avatar = avatar;
        this.isFavorite = isFavorite;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubscribers() {
        return subscribers;
    }

    public void setSubscribers(String subscribers) {
        this.subscribers = subscribers;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Boolean getFavorite() {
        return isFavorite;
    }

    public void setFavorite(Boolean favorite) {
        isFavorite = favorite;
    }
}
