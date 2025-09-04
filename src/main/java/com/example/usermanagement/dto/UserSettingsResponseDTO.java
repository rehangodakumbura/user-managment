package com.example.usermanagement.dto;

import java.time.LocalDateTime;

public class UserSettingsResponseDTO {
    private Long id;
    private Long userId;
    private Boolean notificationEnabled;
    private String theme;
    private String language;
    private LocalDateTime createdAt;

    // Constructors
    public UserSettingsResponseDTO() {}

    public UserSettingsResponseDTO(Long id, Long userId, Boolean notificationEnabled,
                                   String theme, String language) {
        this.id = id;
        this.userId = userId;
        this.notificationEnabled = notificationEnabled;
        this.theme = theme;
        this.language = language;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Boolean getNotificationEnabled() { return notificationEnabled; }
    public void setNotificationEnabled(Boolean notificationEnabled) { this.notificationEnabled = notificationEnabled; }

    public String getTheme() { return theme; }
    public void setTheme(String theme) { this.theme = theme; }

    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}