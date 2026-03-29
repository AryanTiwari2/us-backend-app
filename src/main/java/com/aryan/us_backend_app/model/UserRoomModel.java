package com.aryan.us_backend_app.model;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_rooms")
public class UserRoomModel {

    @EmbeddedId
    private UserRoomId id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "userid")
    public UserModel user;

    @ManyToOne
    @MapsId("roomId")
    @JoinColumn(name = "roomid")
    public RoomModel room;

    public UserRoomModel() {
    }

    public UserRoomModel(UserModel user, RoomModel room) {
        this.user = user;
        this.room = room;
        this.id = new UserRoomId(user.userId, room.roomId);
    }
}
