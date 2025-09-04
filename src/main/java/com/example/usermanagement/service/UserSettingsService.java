package com.example.usermanagement.service;

import com.example.usermanagement.dto.UpdateUserSettingsDTO;
import com.example.usermanagement.dto.UserSettingsResponseDTO;
import com.example.usermanagement.entity.UserSettings;
import com.example.usermanagement.mapper.UserMapper;
import com.example.usermanagement.repository.UserSettingsRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class UserSettingsService {

    private final UserSettingsRepository userSettingsRepository;
    private final UserMapper userMapper;

    public UserSettingsService(UserSettingsRepository userSettingsRepository,
                               UserMapper userMapper) {
        this.userSettingsRepository = userSettingsRepository;
        this.userMapper = userMapper;
    }

    @Cacheable(value = "userSettings", key = "#userId")
    public Optional<UserSettingsResponseDTO> getUserSettings(Long userId) {
        return userSettingsRepository.findByUserId(userId)
                .map(userMapper::toUserSettingsResponseDTO);
    }

    @CacheEvict(value = "userSettings", key = "#updateUserSettingsDTO.userId")
    public UserSettingsResponseDTO updateUserSettings(UpdateUserSettingsDTO updateUserSettingsDTO) {
        UserSettings existing = userSettingsRepository.findByUserId(updateUserSettingsDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User settings not found for user: " + updateUserSettingsDTO.getUserId()));

        userMapper.updateUserSettingsFromDTO(updateUserSettingsDTO, existing);
        UserSettings updated = userSettingsRepository.save(existing);

        return userMapper.toUserSettingsResponseDTO(updated);
    }

    @CacheEvict(value = "userSettings", key = "#userId")
    public void deleteUserSettings(Long userId) {
        userSettingsRepository.deleteByUserId(userId);
    }
}