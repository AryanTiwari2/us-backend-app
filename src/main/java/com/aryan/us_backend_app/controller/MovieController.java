package com.aryan.us_backend_app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aryan.us_backend_app.dto.CreateMovieRequestDto;
import com.aryan.us_backend_app.response.CreateMovieResponse;
import com.aryan.us_backend_app.response.DeletedMovieResponse;
import com.aryan.us_backend_app.service.MovieService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/movie")
public class MovieController {
    @Autowired
    private MovieService movieService;
    @PostMapping("/create")
    public CreateMovieResponse createProfilePage(@Valid @RequestBody CreateMovieRequestDto request , @RequestHeader("token") String token) throws Exception {
        return movieService.createMovie(request, token);
    }

    @GetMapping("/getAllMoviesUsingUserId")
    public List<CreateMovieResponse> getAllMoviesUsingUserId(@RequestHeader("token") String token) throws Exception {
        return movieService.getAllMoviesUsingUserId(token);
    }

    @GetMapping("/getAllMoviesUsingRoomId")
    public List<CreateMovieResponse> getAllMoviesUsingRoomId(@RequestHeader("token") String token) throws Exception {
        return movieService.getAllMoviesUsingRoomId(token);
    }

    @PostMapping("/delete/{movieId}")
    public DeletedMovieResponse deleteMovie(@Valid @PathVariable("movieId") Long movieId , @RequestHeader("token") String token) throws Exception {
        return movieService.deleteMovie(movieId, token);
    }

    @PostMapping("/update/{movieId}")
    public CreateMovieResponse updateMovie(@Valid @PathVariable("movieId") Long movieId , @Valid @RequestBody CreateMovieRequestDto request , @RequestHeader("token") String token) throws Exception {
        return movieService.updateMovie(movieId, request, token);
    }
}
