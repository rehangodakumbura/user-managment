package com.example.usermanagement.service;

import com.example.usermanagement.dto.*;
import com.example.usermanagement.entity.User;
import com.example.usermanagement.entity.UserSettings;
import com.example.usermanagement.mapper.UserMapper;
import com.example.usermanagement.repository.UserRepository;
import com.example.usermanagement.repository.UserSettingsRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final UserSettingsRepository userSettingsRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository,
                       UserSettingsRepository userSettingsRepository,
                       UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userSettingsRepository = userSettingsRepository;
        this.userMapper = userMapper;
    }

    @Cacheable(value = "users")
    public List<UserResponseDTO> getAllUsers() {
        List<User> users = userRepository.findAllActive();
        return userMapper.toResponseDTOList(users);
    }

    @Cacheable(value = "user", key = "#id")
    public Optional<UserResponseDTO> getUserById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::toResponseDTO);
    }

    public Optional<UserResponseDTO> getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(userMapper::toResponseDTO);
    }

    @CacheEvict(value = {"users", "user"}, allEntries = true)
    public UserResponseDTO createUser(CreateUserDTO createUserDTO) {
        if (userRepository.existsByEmail(createUserDTO.getEmail())) {
            throw new RuntimeException("User with email " + createUserDTO.getEmail() + " already exists");
        }

        User user = userMapper.toEntity(createUserDTO);
        User savedUser = userRepository.save(user);

        // Create default user settings
        UserSettings settings = new UserSettings(savedUser.getId());
        userSettingsRepository.save(settings);

        return userMapper.toResponseDTO(savedUser);
    }

    @CacheEvict(value = {"users", "user"}, allEntries = true)
    public UserResponseDTO updateUser(Long id, UpdateUserDTO updateUserDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        userMapper.updateEntityFromDTO(updateUserDTO, user);
        User updatedUser = userRepository.save(user);

        return userMapper.toResponseDTO(updatedUser);
    }

    @CacheEvict(value = {"users", "user"}, allEntries = true)
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        // Soft delete
        user.setDeletedAt(LocalDateTime.now());
        userRepository.save(user);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}