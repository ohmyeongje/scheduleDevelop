package org.example.scheduledevelop.dto;

import lombok.Getter;
import org.example.scheduledevelop.entity.Schedule;


@Getter
public class ScheduleResponseDto {

    private final Long id;

    private final String taskTitle;

    private final String taskContents;

    private final String username;

    //일정 객체를 DTO로 변환하는 메서드
    public ScheduleResponseDto(Long id, String taskTitle, String taskContents, String username) {
        this.id = id;
        this.taskTitle = taskTitle;
        this.taskContents = taskContents;
        this.username = username;
    }

    public static ScheduleResponseDto toDto(Schedule schedule) {
        return new ScheduleResponseDto(
                schedule.getId(),
                schedule.getTaskTitle(),
                schedule.getTaskContents(),
                schedule.getUser().getUsername() //Schedule에 user정보 포함
        );
    }
}
