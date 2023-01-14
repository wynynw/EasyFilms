package com.example.and319;
import android.graphics.Bitmap;
import android.widget.Button;

public class Film {
    private Bitmap filmPoster;
    private String filmName;
    private String filmTimeOn;
    private Button filmSelectButton;
    private double distance;


    public Film(Bitmap filmPoster, String filmName, String filmTimeOn, double distance) {
        this.filmPoster = filmPoster;
        this.filmName = filmName;
        this.filmTimeOn = filmTimeOn;
        this.distance = distance;
    }

    public Bitmap getFilmPoster() {
        return filmPoster;
    }

    public void setFilmPoster(Bitmap filmPoster) {
        this.filmPoster = filmPoster;
    }

    public String getFilmName() {
        return filmName;
    }

    public String getFilmTimeOn() {
        return filmTimeOn;
    }

    public double getDistance() {
        return distance;
    }

}
