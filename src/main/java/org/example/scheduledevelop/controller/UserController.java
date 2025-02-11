package org.example.scheduledevelop.controller;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.scheduledevelop.dto.UpdateEmailRequestDto;
import org.example.scheduledevelop.dto.UserRequestDto;
import org.example.scheduledevelop.dto.UserResponseDto;
import org.example.scheduledevelop.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController // RESTful 웹 서비스의 컨트롤러로 지정
@RequestMapping("/users") // "/users" 경로로 들어오는 요청을 처리
@RequiredArgsConstructor // 생성자를 통한 의존성 주입을 자동으로 생성 (Lombok)
public class UserController {

    private final UserService userService; // UserService 주입 (사용자 관련 비즈니스 로직 처리)

    /**
     * 회원가입 API
     * @param requestDto 회원가입 요청 데이터 (username, email, password)
     * @return 생성된 사용자의 정보를 담은 UserResponseDto
     * @status 201 Created (회원가입 성공)
     */
    @PostMapping("/signup")
    public ResponseEntity<UserResponseDto> signUp(@Valid UserRequestDto requestDto) {
        UserResponseDto userResponseDto =
                userService.signUp(
                        requestDto.getUsername(),
                        requestDto.getEmail(),
                        requestDto.getPassword()
                );

        return new ResponseEntity<>(userResponseDto, HttpStatus.CREATED);
    }

    /**
     * 로그인 API
     * @param requestDto 로그인 요청 데이터 (email, password)
     * @param request HTTP 요청 객체 (세션 생성 및 저장을 위함)
     * @return 로그인 성공 시 사용자 정보를 담은 UserResponseDto
     * @status 200 OK (로그인 성공)
     * @throws 401 Unauthorized (이메일 또는 비밀번호 오류)
     */
    @PostMapping("/login")
    public ResponseEntity<UserResponseDto> login(@RequestBody UserRequestDto requestDto, HttpServletRequest request) {
        UserResponseDto responseDto = userService.login(requestDto.getEmail(), requestDto.getPassword(), request);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    /**
     * 사용자 정보 조회 API
     * @param id 조회할 사용자의 ID
     * @return 사용자 정보를 담은 UserResponseDto
     * @status 200 OK (조회 성공)
     * @throws 404 Not Found (해당 ID의 사용자가 존재하지 않을 경우)
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> findByTd(@PathVariable Long id) {
        UserResponseDto userResponseDto = userService.findById(id);
        return new ResponseEntity<>(userResponseDto, HttpStatus.OK);
    }

    /**
     * 이메일 수정 API
     * @param id 수정할 사용자의 ID
     * @param requestDto 기존 이메일(oldEmail)과 새 이메일(newEmail)을 포함한 요청 데이터
     * @return 응답 데이터 없음 (204 No Content)
     * @status 200 OK (수정 성공)
     * @throws 401 Unauthorized (기존 이메일 불일치)
     */
    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateEmail(@PathVariable Long id, @RequestBody UpdateEmailRequestDto requestDto) {
        userService.updateEmail(id, requestDto.getOldEmail(), requestDto.getNewEmail());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 사용자 삭제 API
     * @param id 삭제할 사용자의 ID
     * @return 응답 데이터 없음 (204 No Content)
     * @status 200 OK (삭제 성공)
     * @throws 404 Not Found (사용자가 존재하지 않을 경우)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
