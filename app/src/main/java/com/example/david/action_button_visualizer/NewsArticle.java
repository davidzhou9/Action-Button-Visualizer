package com.example.david.action_button_visualizer;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by david on 11/24/2017.
 */

public class NewsArticle {
    private String name;
    private String link;
    private String category;
    private LatLng loc;

    public NewsArticle(String name, String link, String category, double latitude, double longitude) {
        this.name = name;
        this.link = link;
        this.category = category;
        this.loc = new LatLng(latitude, longitude);
    }

    public String getName() {
        return name;
    }

    public String getLink() {
        return link;
    }

    public String getCategory() {
        return category;
    }

    public LatLng getLoc() {
        return loc;
    }

    public String toString() {
        return name + " " + link + " " + loc.toString();
    }
}
