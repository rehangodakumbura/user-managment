// src/main/java/com/example/usermanagement/service/UserSettingsService.java

package com.example.usermanagement.service;


import com.example.usermanagement.entity.UserSettings;
import com.example.usermanagement.repository.UserSettingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class UserSettingsService {

    @Autowired
    private UserSettingsRepository userSettingsRepository;

    @Cacheable(value = "userSettings", key = "#userId")
    public Optional<UserSettings> getUserSettings(Long userId) {
        return userSettingsRepository.findByUserId(userId);
    }

    @CacheEvict(value = "userSettings", key = "#settings.userId")
    public UserSettings updateUserSettings(UserSettings settings) {
        UserSettings existing = userSettingsRepository.findByUserId(settings.getUserId())
                .orElseThrow(() -> new RuntimeException("User settings not found for user: " + settings.getUserId()));

        existing.setNotificationEnabled(settings.getNotificationEnabled());
        existing.setTheme(settings.getTheme());
        existing.setLanguage(settings.getLanguage());

        return userSettingsRepository.save(existing);
    }

    @CacheEvict(value = "userSettings", key = "#userId")
    public void deleteUserSettings(Long userId) {
        userSettingsRepository.deleteByUserId(userId);
    }
}
