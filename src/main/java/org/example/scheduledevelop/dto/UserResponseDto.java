package org.example.scheduledevelop.dto;

import lombok.Getter;

@Getter
public class UserResponseDto {

    private final Long id;

    private final String username;

    private final String email;

    private final String password;

    public UserResponseDto(Long id, String username, String email, String password) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
    }
}
