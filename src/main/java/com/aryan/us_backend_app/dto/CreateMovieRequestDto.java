package com.aryan.us_backend_app.dto;

import jakarta.validation.constraints.NotBlank;

public class CreateMovieRequestDto {
    @NotBlank(message = "Movie Name required")
    public String movieName;

    public String description;
    public String watchLink;
    public String genre;
    public String thumbnail;
    public Integer votes;

}
