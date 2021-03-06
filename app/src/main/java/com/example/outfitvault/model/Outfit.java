package com.example.outfitvault.model;

import androidx.annotation.NonNull;

import com.example.outfitvault.types.Season;

/** Stores outfit details id, photoName, description, season, and favorite */
public class Outfit {

    private int id;
    private String photoName;
    private String description;
    private Season season;
    private Boolean favorite;

    public Outfit(int id, String photoName, String description, Season season, Boolean favorite) {
        this.id = id;
        this.photoName = photoName;
        this.description = description;
        this.season = season;
        this.favorite = favorite;
    }

    public int getID() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhotoName() {
        return photoName;
    }

    public void setPhotoName(String photoName) {
        this.photoName = photoName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Season getSeason() {
        return season;
    }

    public void setSeason(Season season) {
        this.season = season;
    }

    public Boolean getFavorite() {
        return favorite;
    }

    public void setFavorite(Boolean favorite) {
        this.favorite = favorite;
    }

    @NonNull
    @Override
    public String toString() {
        return "Outfit{" +
                "id=" + id +
                ", imageName='" + photoName + '\'' +
                ", description='" + description + '\'' +
                ", season=" + season + '\'' +
                ", favorite=" + favorite.toString() + '\'' +
                '}';
    }
}
