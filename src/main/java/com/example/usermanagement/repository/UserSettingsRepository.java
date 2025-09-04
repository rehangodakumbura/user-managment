// src/main/java/com/example/usermanagement/repository/UserSettingsRepository.java

package com.example.usermanagement.repository;


import com.example.usermanagement.entity.UserSettings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserSettingsRepository extends JpaRepository<UserSettings, Long> {

    Optional<UserSettings> findByUserId(Long userId);

    void deleteByUserId(Long userId);
}
