package org.example.scheduledevelop.dto;

import lombok.Getter;

@Getter
public class UpdateScheduleRequestDto {
    private final String oldTaskTitle;
    private final String newTaskTitle;

    public UpdateScheduleRequestDto(String oldTaskTitle, String newTaskTitle) {
        this.oldTaskTitle = oldTaskTitle;
        this.newTaskTitle = newTaskTitle;
    }
}

