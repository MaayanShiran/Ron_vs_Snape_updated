package com.example.ron_vs_mcgonagall;

public class TopTenDetails {

    private String name;
    private int score;
    private int serialNoImg;
    private double latitude;
    private double longitude;

    public TopTenDetails() {
        this.name = "-";
        this.score = 000;
        this.latitude = 0.0;
        this.longitude = 0.0;
    }

    public TopTenDetails(String name, int score, double latitude, double longitude) {
        this.name = name;
        this.score = score;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public String getName() {
        return name;
    }

    public void setLocation(double latitude, double longitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public String getLatitude() {
        return "" + latitude;
    }

    public String getLongitude() {
        return "" + longitude;
    }

    public TopTenDetails setSerialNoImg(int serialNoImg) {
        this.serialNoImg = serialNoImg;
        return this;
    }

    public int getImage() {
        return serialNoImg;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
