package com.diakonia.diakonapp.models;

public class Reward {

    private String title;
    private String location;
    private String expirationDate;
    private int points;
    private String urlPhoto;


    //CONSTRUCTORS
    public Reward(String title, int points) {
        this.title = title;
        this.points = points;
    }

    public Reward(String title, String location, String expirationDate, int points, String urlPhoto) {
        this.title = title;
        this.location = location;
        this.expirationDate = expirationDate;
        this.points = points;
        this.urlPhoto = urlPhoto;
    }

    public Reward() {
    }


    //GETTERS & SETTERS
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getUrlPhoto() {
        return urlPhoto;
    }

    public void setUrlPhoto(String urlPhoto) {
        this.urlPhoto = urlPhoto;
    }
}
