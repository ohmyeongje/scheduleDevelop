package org.example.scheduledevelop.dto;

import lombok.Getter;

@Getter
public class ScheduleResponseDto {

    private final Long id;

    private final String taskTitle;

    private final String taskContents;

    public ScheduleResponseDto(Long id, String taskTitle, String taskContents) {
        this.id = id;
        this.taskTitle = taskTitle;
        this.taskContents = taskContents;
    }
}
