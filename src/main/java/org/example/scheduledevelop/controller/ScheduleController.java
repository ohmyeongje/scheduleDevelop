package org.example.scheduledevelop.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.scheduledevelop.dto.CreateScheduleRequestDto;
import org.example.scheduledevelop.dto.ScheduleResponseDto;
import org.example.scheduledevelop.dto.UpdateScheduleRequestDto;
import org.example.scheduledevelop.service.ScheduleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
@RestController // RESTful 웹 서비스의 컨트롤러로 지정
@RequestMapping("/schedules") // "/schedules" 경로로 들어오는 요청을 처리
@RequiredArgsConstructor // 생성자를 통한 의존성 주입을 자동으로 생성 (Lombok)
public class ScheduleController {

    private final ScheduleService scheduleService; // 일정 관련 비즈니스 로직을 처리하는 서비스 주입

    /**
     * 일정 생성 API
     * @param requestDto 일정 생성 요청 데이터 (taskTitle, taskContents, username)
     * @return 생성된 일정 정보를 담은 ScheduleResponseDto
     * @status 201 Created (일정 생성 성공)
     */
    @PostMapping
    public ResponseEntity<ScheduleResponseDto> save(@RequestBody CreateScheduleRequestDto requestDto) {
        ScheduleResponseDto scheduleResponseDto =
                scheduleService.save(
                        requestDto.getTaskTitle(),
                        requestDto.getTaskContents(),
                        requestDto.getUsername()
                );

        return new ResponseEntity<>(scheduleResponseDto, HttpStatus.CREATED);
    }

    /**
     * 모든 일정 조회 API
     * @return 일정 목록 (List<ScheduleResponseDto>)
     * @status 200 OK (조회 성공)
     */
    @GetMapping
    public ResponseEntity<List<ScheduleResponseDto>> findAll() {
        List<ScheduleResponseDto> scheduleResponseDtoList = scheduleService.findAll();
        return new ResponseEntity<>(scheduleResponseDtoList, HttpStatus.OK);
    }

    /**
     * 일정 수정 API
     * @param id 수정할 일정 ID
     * @param requestDto 일정 수정 요청 데이터 (oldTaskTitle, newTaskTitle)
     * @return 응답 데이터 없음
     * @status 200 OK (수정 성공)
     * @throws 404 Not Found (해당 일정이 존재하지 않을 경우)
     */
    @PatchMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> update(@PathVariable Long id, @RequestBody UpdateScheduleRequestDto requestDto) {
        scheduleService.update(id, requestDto.getOldTaskTitle(), requestDto.getNewTaskTitle());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 일정 삭제 API
     * @param id 삭제할 일정 ID
     * @return 응답 데이터 없음
     * @status 200 OK (삭제 성공)
     * @throws 404 Not Found (해당 일정이 존재하지 않을 경우)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        scheduleService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
