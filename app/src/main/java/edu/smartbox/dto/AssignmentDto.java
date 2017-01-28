package edu.smartbox.dto;

import java.io.Serializable;

/**
 * Created by Ankit on 28/01/17.
 */
public class AssignmentDto implements Serializable{

    String name;
    String url;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
