package org.example.scheduledevelop.service;

import lombok.RequiredArgsConstructor;
import org.example.scheduledevelop.dto.ScheduleResponseDto;
import org.example.scheduledevelop.entity.Schedule;
import org.example.scheduledevelop.entity.User;
import org.example.scheduledevelop.respository.ScheduleRepository;
import org.example.scheduledevelop.respository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final UserRepository userRepository;
    private final ScheduleRepository scheduleRepository;


    public ScheduleResponseDto save(String taskTitle, String taskContents, String username) {

        User findUser = userRepository.findUserByUsernameOrElseThrow(username);

        Schedule schedule = new Schedule(taskTitle, taskContents);

        scheduleRepository.save(schedule);

        return new ScheduleResponseDto(schedule.getId(), schedule.getTaskTitle(), schedule.getTaskContents());
    }
}
