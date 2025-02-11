package org.example.scheduledevelop.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.scheduledevelop.dto.ScheduleResponseDto;
import org.example.scheduledevelop.entity.Schedule;
import org.example.scheduledevelop.entity.User;
import org.example.scheduledevelop.respository.ScheduleRepository;
import org.example.scheduledevelop.respository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
@Slf4j // 로깅을 위한 Lombok 어노테이션
@Service // Spring의 서비스 계층으로 등록
@RequiredArgsConstructor // final 필드에 대한 생성자를 자동 생성 (Lombok)
public class ScheduleService {

    private final UserRepository userRepository; // 사용자 데이터 접근을 위한 레포지토리
    private final ScheduleRepository scheduleRepository; // 일정 데이터 접근을 위한 레포지토리

    /**
     * 일정 생성 메서드
     * @param taskTitle 일정 제목
     * @param taskContents 일정 내용
     * @param username 사용자 이름 (일정을 등록할 사용자)
     * @return 생성된 일정의 정보 (ScheduleResponseDto)
     * @throws ResponseStatusException 404 NOT FOUND - 해당 사용자가 존재하지 않을 경우
     */
    public ScheduleResponseDto save(String taskTitle, String taskContents, String username) {
        // 사용자가 존재하는지 확인
        User findUser = userRepository.findUsernameByElseThrow(username);

        // 새로운 일정 객체 생성 후 저장
        Schedule schedule = new Schedule(taskTitle, taskContents, findUser);
        scheduleRepository.save(schedule);

        // 생성된 일정 정보를 DTO로 변환하여 반환
        return new ScheduleResponseDto(schedule.getId(), schedule.getTaskTitle(), schedule.getTaskContents());
    }

    /**
     * 모든 일정 조회 메서드
     * @return 일정 목록 (List<ScheduleResponseDto>)
     */
    public List<ScheduleResponseDto> findAll() {
        return scheduleRepository.findAll()
                .stream()
                .map(ScheduleResponseDto::toDto) // 엔티티를 DTO로 변환
                .toList();
    }

    /**
     * 일정 수정 메서드
     * @param id 수정할 일정 ID
     * @param oldTaskTitle 기존 일정 제목
     * @param newTaskTitle 새로운 일정 제목
     * @throws ResponseStatusException 404 NOT FOUND - 해당 일정이 존재하지 않을 경우
     * @throws ResponseStatusException 400 BAD REQUEST - 기존 제목이 일치하지 않을 경우
     */
    @Transactional // 변경 사항을 자동으로 커밋하는 트랜잭션 처리
    public void update(Long id, String oldTaskTitle, String newTaskTitle) {
        // ID로 일정 찾기
        Schedule findSchedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "일정을 찾을 수 없습니다."));

        // 기존 제목이 일치하는지 검증
        if (!findSchedule.getTaskTitle().equals(oldTaskTitle)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "잘못된 입력입니다.");
        }

        // 일정 제목 업데이트
        findSchedule.update(newTaskTitle);
    }

    /**
     * 일정 삭제 메서드
     * @param id 삭제할 일정 ID
     * @throws ResponseStatusException 404 NOT FOUND - 해당 일정이 존재하지 않을 경우
     */
    public void delete(Long id) {
        // ID로 일정 찾기 (없으면 예외 발생)
        Schedule schedule = scheduleRepository.findByIdOrElseThrow(id);

        // 일정 삭제
        scheduleRepository.delete(schedule);
    }
}
