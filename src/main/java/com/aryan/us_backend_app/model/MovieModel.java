package com.aryan.us_backend_app.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "movies" , schema = "public")
public class MovieModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long id;

    @Column(name = "movie_name")
    public String movieName;

    @Column(name = "description")
    public String description;

    @Column(name = "watch_link")
    public String watchLink;

    @Column(name = "genre")
    public String genre;

    @Column(name = "votes")
    public Integer votes;

    @Column(name = "thumbnail")
    public String thumbnail;

    @Column(name = "userid")
    public Long userId;

    @Column(name = "roomid")
    public Long roomid;


    public MovieModel() {}
    public MovieModel(String movieName, String description, String watchLink, String genre, Integer votes, String thumbnail, Long userId, Long roomid) {
        this.movieName = movieName;
        this.description = description;
        this.watchLink = watchLink;
        this.genre = genre;
        this.votes = votes;
        this.thumbnail = thumbnail;
        this.userId = userId;
        this.roomid = roomid;
    }
}
