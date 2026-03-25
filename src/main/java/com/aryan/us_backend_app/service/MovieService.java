package com.aryan.us_backend_app.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.aryan.us_backend_app.DbOperation.UserOperation;
import com.aryan.us_backend_app.constants.Constants;
import com.aryan.us_backend_app.constants.JwtUtil;
import com.aryan.us_backend_app.dto.CreateMovieRequestDto;
import com.aryan.us_backend_app.model.MovieModel;
import com.aryan.us_backend_app.model.RoomModel;
import com.aryan.us_backend_app.model.UserModel;
import com.aryan.us_backend_app.response.CreateMovieResponse;
import com.aryan.us_backend_app.response.DeletedMovieResponse;

import io.jsonwebtoken.Claims;

@Service
public class MovieService {
    @Autowired
    private UserOperation userOperation;

    public List<CreateMovieResponse> getAllMoviesUsingUserId(String token) throws Exception {
        Claims claims = JwtUtil.parseToken(token);
        Long userId = ((Number) claims.get("userId")).longValue();
        String username = (String) claims.get("username");
        if (userId == null || userId == 0) {
            throw new Exception("Invalid token");
        }
        List<MovieModel> movies = userOperation.getAllMoviesUsingUserId(userId);
        List<CreateMovieResponse> createMovieResponses = new java.util.ArrayList<>();
        for(int i = 0; i < movies.size(); i++){
            RoomModel room = userOperation.getRoomById(movies.get(i).roomid);
            CreateMovieResponse createMovieResponse = new CreateMovieResponse(movies.get(i), username, room.roomName);
            createMovieResponses.add(createMovieResponse);
        }
        return createMovieResponses;
    }

    public List<CreateMovieResponse> getAllMoviesUsingRoomId(String token) throws Exception {
        Claims claims = JwtUtil.parseToken(token);
        String roomName = (String) claims.get("roomName");
        if (roomName == null) {
            throw new Exception("Invalid token");
        }
        RoomModel room = userOperation.getRoomByName(roomName);
        List<MovieModel> movies = userOperation.getAllMoviesUsingRoomId(room.roomId);

        List<CreateMovieResponse> createMovieResponses = new java.util.ArrayList<>();
        for(int i = 0; i < movies.size(); i++){
            UserModel user = userOperation.getUserByUserId(movies.get(i).userId);
            CreateMovieResponse createMovieResponse = new CreateMovieResponse(movies.get(i), user.username, room.roomName);
            createMovieResponses.add(createMovieResponse);
        }
        return createMovieResponses;
    }

    public CreateMovieResponse createMovie(CreateMovieRequestDto request, String token) throws Exception {
        Claims claims = JwtUtil.parseToken(token);
        String roomName = (String) claims.get("roomName");
        String username = (String) claims.get("username");
        if (roomName == null || username == null) {
            throw new Exception("Invalid token");
        }
        RoomModel room = userOperation.getRoomByName(roomName);
        UserModel user = userOperation.getUserByName(username);
        MovieModel movie = new MovieModel();
        movie.movieName = request.movieName;
        if (request.description != null)
            movie.description = request.description;
        if (request.watchLink != null)
            movie.watchLink = request.watchLink;
        if (request.genre != null)
            movie.genre = request.genre;
        if (request.votes != null)
            movie.votes = request.votes;
        if (request.thumbnail != null)
            movie.thumbnail = request.thumbnail;
        movie.roomid = room.roomId;
        movie.userId = user.userId;
        MovieModel savedMovie = userOperation.saveMovie(movie);
        return new CreateMovieResponse(savedMovie , user.username , room.roomName);
    }

    public DeletedMovieResponse deleteMovie(Long movieId, String token) throws Exception {
        Claims claims = JwtUtil.parseToken(token);
        String username = (String) claims.get("username");
        String userTyeStr = (String) claims.get("userType");
        if (username == null || userTyeStr == null) {
            throw new Exception("Invalid token");
        }

        if((!userTyeStr.equals(Constants.UserType.ADMIN.toString()) && !userTyeStr.equals(Constants.UserType.MODERATOR.toString())) && 
        !username.equals(userOperation.getUserByName(username).username)) 
        {
            throw new Exception("Only admin or moderator can delete movie of other user or user can delete their own movie");
        }

        boolean isDeleted = userOperation.deleteMovie(movieId);

        if(!isDeleted){
            throw new Exception("Movie does not exist");
        }

        return new DeletedMovieResponse("Movie deleted successfully", true);
    }

    public CreateMovieResponse updateMovie(Long movieId, CreateMovieRequestDto request, String token) throws Exception {
        Claims claims = JwtUtil.parseToken(token);
        String roomName = (String) claims.get("roomName");
        String username = (String) claims.get("username");
        if (roomName == null || username == null) {
            throw new Exception("Invalid token");
        }
        RoomModel room = userOperation.getRoomByName(roomName);
        UserModel user = userOperation.getUserByName(username);
        MovieModel movie = new MovieModel();
        movie.movieName = request.movieName;
        if (request.description != null)
            movie.description = request.description;
        if (request.watchLink != null)
            movie.watchLink = request.watchLink;
        if (request.genre != null)
            movie.genre = request.genre;
        if (request.votes != null)
            movie.votes = request.votes;
        if (request.thumbnail != null)
            movie.thumbnail = request.thumbnail;
        movie.roomid = room.roomId;
        movie.userId = user.userId;
        MovieModel savedMovie = userOperation.updateMovie(movieId,movie);
        return new CreateMovieResponse(savedMovie, user.username, room.roomName);
    }
}
