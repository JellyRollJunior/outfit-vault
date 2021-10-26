package com.example.outfitvault.model;

import com.example.outfitvault.types.Season;

public class Outfit {
    private String image;
    private String outer;
    private String inner;
    private String bottom;
    private String footwear;
    private String description;
    private Season season;

    public Outfit(String image, String outer, String inner, String bottom, String footwear, String description, Season season) {
        this.image = image;
        this.outer = outer;
        this.inner = inner;
        this.bottom = bottom;
        this.footwear = footwear;
        this.description = description;
        this.season = season;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getOuter() {
        return outer;
    }

    public void setOuter(String outer) {
        this.outer = outer;
    }

    public String getInner() {
        return inner;
    }

    public void setInner(String inner) {
        this.inner = inner;
    }

    public String getBottom() {
        return bottom;
    }

    public void setBottom(String bottom) {
        this.bottom = bottom;
    }

    public String getFootwear() {
        return footwear;
    }

    public void setFootwear(String footwear) {
        this.footwear = footwear;
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
                "image='" + image + '\'' +
                ", outer='" + outer + '\'' +
                ", inner='" + inner + '\'' +
                ", bottom='" + bottom + '\'' +
                ", footwear='" + footwear + '\'' +
                ", description='" + description + '\'' +
                ", season=" + season +
                '}';
    }
}
