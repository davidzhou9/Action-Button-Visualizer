package com.example.david.action_button_visualizer;

import com.google.android.gms.maps.model.LatLng;

/**
 * Class description: Helper class to store attributes about articles
 */

public class NewsArticle {
    private String name;
    private String link;
    private String category;
    private LatLng loc;

    /**
     * Constructor with article params
     * @param name
     * @param link
     * @param category
     * @param latitude
     * @param longitude
     */
    public NewsArticle(String name, String link, String category, double latitude, double longitude) {
        this.name = name;
        this.link = link;
        this.category = category;
        this.loc = new LatLng(latitude, longitude);
    }

    /**
     * Getter method for article name
     * @return String
     */
    public String getName() {
        return name;
    }

    /**
     * Getter method for article link (i.e. url)
     * @return String
     */
    public String getLink() {
        return link;
    }

    /**
     * Getter method for category
     * @return String
     */
    public String getCategory() {
        return category;
    }

    /**
     * Getter method for latitude longitude location
     * @return LatLng
     */
    public LatLng getLoc() {
        return loc;
    }

    /**
     * To String method -> return all instance variables
     * @return String
     */
    public String toString() {
        return name + " " + link + " " + loc.toString();
    }
}
