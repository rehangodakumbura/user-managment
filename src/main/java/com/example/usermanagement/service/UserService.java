// src/main/java/com/example/usermanagement/service/UserService.java

package com.example.usermanagement.service;


import com.example.usermanagement.entity.User;
import com.example.usermanagement.entity.UserSettings;
import com.example.usermanagement.repository.UserRepository;
import com.example.usermanagement.repository.UserSettingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserSettingsRepository userSettingsRepository;

    @Cacheable(value = "users")
    public List<User> getAllUsers() {
        return userRepository.findAllActive();
    }

    @Cacheable(value = "user", key = "#id")
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @CacheEvict(value = {"users", "user"}, allEntries = true)
    public User createUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("User with email " + user.getEmail() + " already exists");
        }

        User savedUser = userRepository.save(user);

        // Create default user settings
        UserSettings settings = new UserSettings(savedUser.getId());
        userSettingsRepository.save(settings);

        return savedUser;
    }

    @CacheEvict(value = {"users", "user"}, allEntries = true)
    public User updateUser(Long id, User userDetails) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        user.setFirstName(userDetails.getFirstName());
        user.setLastName(userDetails.getLastName());
        user.setEmail(userDetails.getEmail());
        user.setDob(userDetails.getDob());
        user.setPhoneCountryCode(userDetails.getPhoneCountryCode());
        user.setPhoneNumber(userDetails.getPhoneNumber());
        user.setProfilePic(userDetails.getProfilePic());
        user.setEmailVerified(userDetails.getEmailVerified());
        user.setPhoneVerified(userDetails.getPhoneVerified());

        return userRepository.save(user);
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
