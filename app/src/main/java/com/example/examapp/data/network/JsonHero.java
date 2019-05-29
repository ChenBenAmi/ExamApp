package com.example.examapp.data.network;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class JsonHero {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("abilities")
    @Expose
    private List<String> abilities = null;
    @SerializedName("image")
    @Expose
    private String image;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getAbilities() {
        return abilities;
    }

    public void setAbilities(List<String> abilities) {
        this.abilities = abilities;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "JsonHero{" +
                "title='" + title + '\'' +
                ", abilities=" + abilities +
                ", image='" + image + '\'' +
                '}';
    }
}
