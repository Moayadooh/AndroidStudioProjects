package com.example.firebase;

public class Track {
    private String id;
    private String trackName;
    private int rating;

    public Track() {

    }

    public Track(String id, String trackName, int rating) {
        this.id = id;
        this.trackName = trackName;
        this.rating = rating;
    }

    public String getTrackId() {
        return id;
    }

    public String getTrackName() {
        return trackName;
    }

    public int getRating() {
        return rating;
    }
}
