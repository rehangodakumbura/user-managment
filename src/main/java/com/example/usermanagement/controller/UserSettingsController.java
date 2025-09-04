// src/main/java/com/example/usermanagement/controller/UserSettingsController.java

package com.example.usermanagement.controller;


import com.example.usermanagement.entity.UserSettings;
import com.example.usermanagement.service.UserSettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user-settings")
@CrossOrigin(origins = "*")
public class UserSettingsController {

    @Autowired
    private UserSettingsService userSettingsService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<UserSettings> getUserSettings(@PathVariable Long userId) {
        return userSettingsService.getUserSettings(userId)
                .map(settings -> ResponseEntity.ok(settings))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping
    public ResponseEntity<UserSettings> updateUserSettings(@RequestBody UserSettings settings) {
        try {
            UserSettings updatedSettings = userSettingsService.updateUserSettings(settings);
            return ResponseEntity.ok(updatedSettings);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
