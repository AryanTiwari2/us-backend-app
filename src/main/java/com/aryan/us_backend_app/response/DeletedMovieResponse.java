package com.aryan.us_backend_app.response;

public class DeletedMovieResponse {
    public String message;
    public boolean isDeleted;

    public DeletedMovieResponse(String message, boolean isDeleted) {
        this.message = message;
        this.isDeleted = isDeleted;
    }
}
