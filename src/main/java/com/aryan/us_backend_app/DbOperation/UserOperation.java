package com.aryan.us_backend_app.DbOperation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.aryan.us_backend_app.constants.Constants;
import com.aryan.us_backend_app.model.MovieModel;
import com.aryan.us_backend_app.model.RoomModel;
import com.aryan.us_backend_app.model.UserModel;
import com.aryan.us_backend_app.model.UserRoomModel;
import com.aryan.us_backend_app.repository.MovieRepository;
import com.aryan.us_backend_app.repository.RoomRepository;
import com.aryan.us_backend_app.repository.UserRepository;
import com.aryan.us_backend_app.repository.UserRoomRepository;

@Component
public class UserOperation {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private UserRoomRepository userRoomRepository;

    public UserModel getUserByName(String name) {
        return userRepository.findByUsername(name);
    }

    public UserModel getUserByIdAndName(Long id, String name) {
        return userRepository.findByUsernameAndUserId(name, id);
    }

    public RoomModel getRoomByName(String roomName) {
        return roomRepository.findByRoomName(roomName);
    }

    public RoomModel getRoomById(Long roomId) {
        return roomRepository.findByRoomId(roomId);
    }

    public UserModel getUserByUserId(Long userId) {
        return userRepository.findByUserId(userId);
    }

    public List<RoomModel> getAllRooms() {
        return roomRepository.findAll();
    }

    public UserModel getUserByNameAndRoom(String name, String roomName) throws Exception {
        UserModel user = userRepository.findByUsername(name);
        if (user == null)
            throw new Exception("User does not exist");

        RoomModel room = roomRepository.findByRoomName(roomName);
        if (room == null)
            throw new Exception("Room does not exist");

        if (userRoomRepository.existsByUser_UserIdAndRoom_RoomId(user.userId, room.roomId))
            return user;

        if (room.roomType.equals(Constants.RoomType.PUBLIC.toString()))
            return this.updateUsersRoom(user, room.roomName);

        throw new Exception("User is not in the room");
    }

    public UserModel saveUser(UserModel user) {
        return userRepository.save(user);
    }

    public UserModel createUser(String username, String password, String roomName) {

        UserModel existingUser = userRepository.findByUsername(username);
        if (existingUser != null)
            return null;

        PasswordEncoder encoder = new BCryptPasswordEncoder();
        String hashedPassword = encoder.encode(password);

        UserModel user = new UserModel(username, hashedPassword);

        RoomModel room = roomRepository.findByRoomName(roomName);
        if (room == null)
            return null;

        UserModel savedUser = userRepository.save(user);

        userRoomRepository.save(new UserRoomModel(savedUser, room));
        return savedUser;
    }

    public RoomModel createRoom(String roomName, String roomType) throws Exception {
        if (roomRepository.findByRoomName(roomName) != null)
            throw new Exception("Room already exists");

        RoomModel room = new RoomModel(roomName);
        Constants.RoomType roomTypeEnum = Constants.RoomType.valueOf(roomType);
        room.roomType = roomTypeEnum.toString();
        return roomRepository.save(room);
    }

    public RoomModel updateRoom(String roomName, String roomType) throws Exception {
        RoomModel room = roomRepository.findByRoomName(roomName);

        if (room == null)
            throw new Exception("Room does not exist");

        Constants.RoomType roomTypeEnum = Constants.RoomType.valueOf(roomType);
        room.roomType = roomTypeEnum.toString();
        return roomRepository.save(room);
    }

    public UserModel updateUsersRoom(UserModel user, String roomName) {
        RoomModel existingRoom = roomRepository.findByRoomName(roomName);
        if (existingRoom == null)
            return null;

        if (userRoomRepository.existsByUser_UserIdAndRoom_RoomId(user.userId, existingRoom.roomId))
            return user;

        userRoomRepository.save(new UserRoomModel(user, existingRoom));

        return userRepository.save(user);
    }

    public UserModel updateUserType(String username, String userType) {
        Constants.UserType userTypeEnum = Constants.UserType.valueOf(userType);

        UserModel existingUser = userRepository.findByUsername(username);
        if (existingUser == null)
            return null;

        existingUser.userType = userTypeEnum.toString();

        return userRepository.save(existingUser);
    }

    public UserModel removeUserRoom(UserModel user, String roomName) throws Exception {

        RoomModel existingRoom = roomRepository.findByRoomName(roomName);
        if (existingRoom == null)
            throw new Exception("Room does not exist");

        if (!userRoomRepository.existsByUser_UserIdAndRoom_RoomId((user.userId), existingRoom.roomId))
            throw new Exception("User is not in the room");

        userRoomRepository.deleteByUser_UserIdAndRoom_RoomId(user.userId, existingRoom.roomId);

        return userRepository.save(user);
    }

    public List<MovieModel> getAllMoviesUsingUserId(Long userId) {
        return movieRepository.findByUserId(userId);
    }

    public List<MovieModel> getAllMoviesUsingRoomId(Long roomId) {
        return movieRepository.findByRoomid(roomId);
    }

    public MovieModel saveMovie(MovieModel movie) throws Exception {
        MovieModel existingMovie = movieRepository.findByMovieName(movie.movieName);
        if (existingMovie == null) {
            return movieRepository.save(movie);
        }
        throw new Exception("Movie already exists");
    }

    public boolean deleteMovie(Long movieId) throws Exception {
        boolean isExists = movieRepository.existsById(movieId);
        if (!isExists) {
            throw new Exception("Movie does not exist");
        }
        movieRepository.deleteById(movieId);
        return true;
    }

    public MovieModel updateMovie(Long movieId, MovieModel movie) throws Exception {
        MovieModel existingMovie = movieRepository.findById(movieId)
                .orElseThrow(() -> new Exception("Movie does not exist"));

        if(existingMovie.userId != movie.userId){
            throw new Exception("You are not authorized to update this movie");
        }

        if(existingMovie.roomid != movie.roomid){
            throw new Exception("You are not in right room to update this movie");
        }

        // update only fields you want
        if (movie.movieName != null)
            existingMovie.movieName = movie.movieName;

        if (movie.description != null)
            existingMovie.description = movie.description;

        if (movie.watchLink != null)
            existingMovie.watchLink = movie.watchLink;

        if (movie.genre != null)
            existingMovie.genre = movie.genre;

        if (movie.votes != null)
            existingMovie.votes = movie.votes;

        if (movie.thumbnail != null)
            existingMovie.thumbnail = movie.thumbnail;

        return movieRepository.save(existingMovie);
    }

    public List<RoomModel> getAllUsersRoom(Long userId) {
        List<UserRoomModel> userRooms = userRoomRepository.findByUser_UserId(userId);
        List<RoomModel> rooms = new ArrayList<>();
        for (UserRoomModel userRoom : userRooms) {
            rooms.add(userRoom.room);
        }
        return rooms;
    }

    public List<UserModel> getAllUsersInRoom(Long roomId) {
        List<UserRoomModel> userRooms = userRoomRepository.findByRoom_RoomId(roomId);
        List<UserModel> users = new ArrayList<>();
        for (UserRoomModel userRoom : userRooms) {
            users.add(userRoom.user);
        }
        return users;
    }
}
