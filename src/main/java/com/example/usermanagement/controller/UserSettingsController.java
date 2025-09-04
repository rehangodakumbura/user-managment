package com.example.usermanagement.controller;

import com.example.usermanagement.dto.UpdateUserSettingsDTO;
import com.example.usermanagement.dto.UserSettingsResponseDTO;
import com.example.usermanagement.service.UserSettingsService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user-settings")
@CrossOrigin(origins = "*")
public class UserSettingsController {

    private final UserSettingsService userSettingsService;

    public UserSettingsController(UserSettingsService userSettingsService) {
        this.userSettingsService = userSettingsService;
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<UserSettingsResponseDTO> getUserSettings(@PathVariable Long userId) {
        return userSettingsService.getUserSettings(userId)
                .map(settings -> ResponseEntity.ok(settings))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping
    public ResponseEntity<UserSettingsResponseDTO> updateUserSettings(@Valid @RequestBody UpdateUserSettingsDTO updateUserSettingsDTO) {
        try {
            UserSettingsResponseDTO updatedSettings = userSettingsService.updateUserSettings(updateUserSettingsDTO);
            return ResponseEntity.ok(updatedSettings);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}