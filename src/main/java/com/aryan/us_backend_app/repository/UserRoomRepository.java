package com.aryan.us_backend_app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.aryan.us_backend_app.model.UserRoomModel;

import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface UserRoomRepository extends JpaRepository<UserRoomModel, Long> {
    boolean existsByUser_UserIdAndRoom_RoomId(Long userId, Long roomId);
    UserRoomModel findByUser_UserIdAndRoom_RoomId(Long userId, Long roomId);
    List<UserRoomModel> findByUser_UserId(Long userId);
    List<UserRoomModel> findByRoom_RoomId(Long roomId);
    void deleteByUser_UserIdAndRoom_RoomId(Long userId, Long roomId);
}

