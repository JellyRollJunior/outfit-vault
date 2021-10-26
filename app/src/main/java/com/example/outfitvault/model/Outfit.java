package com.example.outfitvault.model;

import com.example.outfitvault.types.Season;

public class Outfit {

    private int id;
    private String imageName;
    private String description;
    private Season season;

    public Outfit(int id, String imageName, String description, Season season) {
        this.id = id;
        this.imageName = imageName;
        this.description = description;
        this.season = season;
    }

    public int getId() {
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

    @Override
    public String toString() {
        return "Outfit{" +
                "description='" + description + '\'' +
                ", season=" + season +
                '}';
    }
}
