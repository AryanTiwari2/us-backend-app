package com.aryan.us_backend_app.response;
import com.aryan.us_backend_app.model.MovieModel;

public class CreateMovieResponse {
    public MovieModel movie;
    public String username;
    public String roomName;

    public CreateMovieResponse(MovieModel movie, String username , String roomName) {
        this.movie = movie;
        this.username = username;
        this.roomName = roomName;
    }
}
