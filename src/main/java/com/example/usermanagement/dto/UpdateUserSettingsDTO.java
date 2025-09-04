package com.example.usermanagement.dto;

import jakarta.validation.constraints.NotNull;

public class UpdateUserSettingsDTO {

    @NotNull(message = "User ID is required")
    private Long userId;

    private Boolean notificationEnabled;
    private String theme;
    private String language;

    // Constructors
    public UpdateUserSettingsDTO() {}

    public UpdateUserSettingsDTO(Long userId, Boolean notificationEnabled,
                                 String theme, String language) {
        this.userId = userId;
        this.notificationEnabled = notificationEnabled;
        this.theme = theme;
        this.language = language;
    }

    // Getters and Setters
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Boolean getNotificationEnabled() { return notificationEnabled; }
    public void setNotificationEnabled(Boolean notificationEnabled) { this.notificationEnabled = notificationEnabled; }

    public String getTheme() { return theme; }
    public void setTheme(String theme) { this.theme = theme; }

    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; }
}