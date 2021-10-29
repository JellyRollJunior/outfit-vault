package com.example.outfitvault.model;

import com.example.outfitvault.types.Season;

public class Outfit {

    private int id;
    private String imageName;
    private String description;
    private Season season;
    private Boolean favorite;

    public Outfit(int id, String imageName, String description, Season season, Boolean favorite) {
        this.id = id;
        this.imageName = imageName;
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

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
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

    @Override
    public String toString() {
        return "Outfit{" +
                "id=" + id +
                ", imageName='" + imageName + '\'' +
                ", description='" + description + '\'' +
                ", season=" + season + '\'' +
                ", favorite=" + favorite.toString() + '\'' +
                '}';
    }
}
