package com.example.td3_asl.Favorite;

import androidx.room.ColumnInfo;

public class FavoriteItem {
    @ColumnInfo(name = "prname")
    private String name;
    private String urlImage;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }
}