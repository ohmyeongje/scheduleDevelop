package org.example.scheduledevelop.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class CreateScheduleRequestDto {

    private final String username;

    private final String taskTitle;

    private final String taskContents;

    public CreateScheduleRequestDto(String username, String taskTitle, String taskContents) {
        this.username = username;
        this.taskTitle = taskTitle;
        this.taskContents = taskContents;
    }
}
