package org.example.scheduledevelop.dto;

import jakarta.persistence.GeneratedValue;
import lombok.Getter;

@Getter
public class UpdateEmailRequestDto {

    private final String oldEmail;

    private final String newEmail;

    public UpdateEmailRequestDto(String oldEmail, String newEmail) {
        this.oldEmail = oldEmail;
        this.newEmail = newEmail;
    }
}
