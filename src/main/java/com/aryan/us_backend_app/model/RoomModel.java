package com.aryan.us_backend_app.model;

import java.util.List;

import org.apache.tomcat.util.bcel.Const;
import org.apache.tomcat.util.bcel.classfile.Constant;

import com.aryan.us_backend_app.constants.Constants;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "rooms", schema = "public")
public class RoomModel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "roomid")
    public Long roomId;

    @Column(name = "room_name")
    public String roomName;

    @Column(name = "room_type")
    public String roomType;

    public RoomModel() {}
    public RoomModel(String roomName) {
        this.roomName = roomName;
        this.roomType = Constants.RoomType.PUBLIC.toString();
    }
}
