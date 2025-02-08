package org.example.scheduledevelop.controller;


import lombok.RequiredArgsConstructor;
import org.example.scheduledevelop.dto.CreateScheduleRequestDto;
import org.example.scheduledevelop.dto.ScheduleResponseDto;
import org.example.scheduledevelop.service.ScheduleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schedules")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping
    public ResponseEntity<ScheduleResponseDto> save(@RequestBody CreateScheduleRequestDto requestDto) {

        ScheduleResponseDto scheduleResponseDto =
                scheduleService.save(
                        requestDto.getTaskTitle(),
                        requestDto.getTaskContents(),
                        requestDto.getUsername()
                );

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ScheduleResponseDto>> findAll() {

        List<ScheduleResponseDto> scheduleResponseDtoList = scheduleService.findAll();

        return new ResponseEntity<>(scheduleResponseDtoList, HttpStatus.OK);
    }
}
