package com.aryan.us_backend_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aryan.us_backend_app.model.RoomModel;

@Repository
public interface RoomRepository extends JpaRepository<RoomModel, Long> {
   RoomModel findByRoomName(String roomName);
}