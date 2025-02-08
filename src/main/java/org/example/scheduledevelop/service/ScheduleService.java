package org.example.scheduledevelop.service;

import lombok.RequiredArgsConstructor;
import org.example.scheduledevelop.dto.ScheduleResponseDto;
import org.example.scheduledevelop.entity.Schedule;
import org.example.scheduledevelop.entity.User;
import org.example.scheduledevelop.respository.ScheduleRepository;
import org.example.scheduledevelop.respository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final UserRepository userRepository;
    private final ScheduleRepository scheduleRepository;


    public ScheduleResponseDto save(String taskTitle, String taskContents, String username) {
        User findUser = userRepository.findUsernameByElseThrow(username);

        Schedule schedule = new Schedule(taskTitle, taskContents, findUser);
        scheduleRepository.save(schedule);

        return new ScheduleResponseDto(schedule.getId(), schedule.getTaskTitle(), schedule.getTaskContents());
    }

    public List<ScheduleResponseDto> findAll() {
        return scheduleRepository.findAll().stream().map(ScheduleResponseDto::toDto).toList();
    }
}