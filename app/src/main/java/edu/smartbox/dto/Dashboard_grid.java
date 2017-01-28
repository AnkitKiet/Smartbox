package edu.smartbox.dto;

/**
 * Created by Ankit on 07/01/17.
 */
public class Dashboard_grid {
    private String name;
    private int thumbnail;

    public Dashboard_grid() {
    }

    public Dashboard_grid(String name, int thumbnail) {
        this.name = name;
        this.thumbnail = thumbnail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public int getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }
}

