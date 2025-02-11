package org.example.scheduledevelop.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UpdateScheduleRequestDto {

    @NotBlank(message = "일정 제목은 필수입니다.")
    private final String oldTaskTitle;

    @NotBlank(message = "새로운 일정 제목은 필수입니다")
    private final String newTaskTitle;

    public UpdateScheduleRequestDto(String oldTaskTitle, String newTaskTitle) {
        this.oldTaskTitle = oldTaskTitle;
        this.newTaskTitle = newTaskTitle;
    }
}

