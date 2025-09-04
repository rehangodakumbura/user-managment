package com.example.usermanagement.mapper;

import com.example.usermanagement.dto.*;
import com.example.usermanagement.entity.User;
import com.example.usermanagement.entity.UserSettings;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    public UserResponseDTO toResponseDTO(User user) {
        if (user == null) return null;

        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        dto.setDob(user.getDob());
        dto.setPhoneCountryCode(user.getPhoneCountryCode());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setProfilePic(user.getProfilePic());
        dto.setEmailVerified(user.getEmailVerified());
        dto.setPhoneVerified(user.getPhoneVerified());
        dto.setCreatedAt(user.getCreatedAt());

        if (user.getUserSettings() != null) {
            dto.setUserSettings(toUserSettingsResponseDTO(user.getUserSettings()));
        }

        return dto;
    }

    public List<UserResponseDTO> toResponseDTOList(List<User> users) {
        return users.stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public User toEntity(CreateUserDTO dto) {
        if (dto == null) return null;

        User user = new User();
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        user.setDob(dto.getDob());
        user.setPhoneCountryCode(dto.getPhoneCountryCode());
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setProfilePic(dto.getProfilePic());

        return user;
    }

    public void updateEntityFromDTO(UpdateUserDTO dto, User user) {
        if (dto == null || user == null) return;

        if (dto.getFirstName() != null) {
            user.setFirstName(dto.getFirstName());
        }
        if (dto.getLastName() != null) {
            user.setLastName(dto.getLastName());
        }
        if (dto.getEmail() != null) {
            user.setEmail(dto.getEmail());
        }
        if (dto.getDob() != null) {
            user.setDob(dto.getDob());
        }
        if (dto.getPhoneCountryCode() != null) {
            user.setPhoneCountryCode(dto.getPhoneCountryCode());
        }
        if (dto.getPhoneNumber() != null) {
            user.setPhoneNumber(dto.getPhoneNumber());
        }
        if (dto.getProfilePic() != null) {
            user.setProfilePic(dto.getProfilePic());
        }
        if (dto.getEmailVerified() != null) {
            user.setEmailVerified(dto.getEmailVerified());
        }
        if (dto.getPhoneVerified() != null) {
            user.setPhoneVerified(dto.getPhoneVerified());
        }
    }

    public UserSettingsResponseDTO toUserSettingsResponseDTO(UserSettings settings) {
        if (settings == null) return null;

        UserSettingsResponseDTO dto = new UserSettingsResponseDTO();
        dto.setId(settings.getId());
        dto.setUserId(settings.getUserId());
        dto.setNotificationEnabled(settings.getNotificationEnabled());
        dto.setTheme(settings.getTheme());
        dto.setLanguage(settings.getLanguage());
        dto.setCreatedAt(settings.getCreatedAt());

        return dto;
    }

    public void updateUserSettingsFromDTO(UpdateUserSettingsDTO dto, UserSettings settings) {
        if (dto == null || settings == null) return;

        if (dto.getNotificationEnabled() != null) {
            settings.setNotificationEnabled(dto.getNotificationEnabled());
        }
        if (dto.getTheme() != null) {
            settings.setTheme(dto.getTheme());
        }
        if (dto.getLanguage() != null) {
            settings.setLanguage(dto.getLanguage());
        }
    }
}