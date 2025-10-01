package com.aryan.us_backend_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.aryan.us_backend_app.model.UserModel;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {
    UserModel findByUsernameAndUserId(String username, Long userId);
    UserModel findByUsername(String username);
    boolean existsByUserIdAndRoomsRoomId(Long userId, Long roomId);
}