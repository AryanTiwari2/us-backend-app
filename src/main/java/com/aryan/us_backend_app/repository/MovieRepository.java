package com.aryan.us_backend_app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.aryan.us_backend_app.model.MovieModel;

@Repository
public interface MovieRepository extends JpaRepository<MovieModel, Long> {
  MovieModel findByMovieName(String movieName);
  List<MovieModel> findByUserId(Long userId);
  List<MovieModel> findByRoomid(Long roomId);
    
}