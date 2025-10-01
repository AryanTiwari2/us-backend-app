package com.aryan.us_backend_app.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.tomcat.util.bcel.Const;

import com.aryan.us_backend_app.constants.Constants;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;

@Entity
@Table(name = "users", schema = "public")
public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userid")
    public Long userId;
    
    @Column(name = "username")
    public String username;
    
    @Column(name = "user_type")
    public String userType;
    
     @Column(name = "password")
    public String password;

    @ManyToMany
    @JoinTable(name = "user_rooms", joinColumns = @JoinColumn(name = "userid"), inverseJoinColumns = @JoinColumn(name = "roomid"))
    public List<RoomModel> rooms;

    // Constructors
    public UserModel() {}

    public UserModel(String username, String password) {
        this.username = username;
        this.password = password;
        this.userType = Constants.UserType.REGULAR.toString();
        this.rooms = new ArrayList<>();
    }
}
