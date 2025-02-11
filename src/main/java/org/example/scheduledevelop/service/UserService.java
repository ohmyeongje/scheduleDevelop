package org.example.scheduledevelop.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.scheduledevelop.config.PasswordEncoder;
import org.example.scheduledevelop.dto.UserResponseDto;
import org.example.scheduledevelop.entity.User;
import org.example.scheduledevelop.respository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
@Slf4j // 로깅을 위한 Lombok 어노테이션
@Service // Spring의 서비스 계층으로 등록
@RequiredArgsConstructor // final 필드에 대한 생성자를 자동 생성 (Lombok)
public class UserService {

    private final UserRepository userRepository; // 사용자 데이터를 관리하는 JPA 레포지토리
    private final PasswordEncoder passwordEncoder; //사용자 비밀번호 암호화
    /**
     * 회원가입 메서드
     * @param username 사용자 이름
     * @param email 사용자 이메일
     * @param password 사용자 비밀번호
     * @return 회원가입된 사용자의 정보 (UserResponseDto)
     */
    public UserResponseDto signUp(String username, String email, String password) {
        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(password);

        // 암호화된 비밀번호로 사용자 객체 생성
        User user = new User(username, email, encodedPassword);

        // 사용자 정보 저장
        User savedUser = userRepository.save(user);

        // 저장된 사용자 정보를 DTO로 변환하여 반환
        return new UserResponseDto(savedUser.getId(), savedUser.getUsername(), savedUser.getEmail(), savedUser.getPassword());
    }

    /**
     * 로그인 메서드
     * @param email 로그인할 이메일
     * @param password 로그인할 비밀번호
     * @param request HTTP 요청 객체 (세션 저장을 위해 사용)
     * @return 로그인한 사용자 정보 (UserResponseDto)
     * @throws ResponseStatusException 401 UNAUTHORIZED - 이메일이 없거나 비밀번호가 틀릴 경우
     */
    public UserResponseDto login(String email, String password, HttpServletRequest request) {
        // 이메일을 이용해 사용자 찾기 (없으면 401 에러 반환)
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "이메일 또는 비밀번호가 잘못되었습니다."));

        // 여기서 null 체크 추가
        if (user.getPassword() == null || !user.getPassword().equals(password)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "이메일 또는 비밀번호가 잘못되었습니다.");
        }

        // 세션 생성 및 사용자 정보 저장
        HttpSession session = request.getSession(true);
        session.setAttribute("user", user);

        log.info("로그인 성공! 세션 저장: {} (사용자: {})", session.getId(), user);

        return new UserResponseDto(user.getId(), user.getUsername(), user.getEmail(), user.getPassword());
    }

    /**
     * 사용자 조회 메서드
     * @param id 조회할 사용자 ID
     * @return 조회된 사용자 정보 (UserResponseDto)
     * @throws ResponseStatusException 404 NOT FOUND - 존재하지 않는 사용자 ID 요청 시
     */
    public UserResponseDto findById(Long id) {
        if (id == null || id <= 0) {  // 🔹 ID 값 검증
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "잘못된 사용자 ID입니다.");
        }

        User findUser = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."));

        return new UserResponseDto(findUser.getId(), findUser.getUsername(), findUser.getEmail(), findUser.getPassword());
    }


    /**
     * 이메일 수정 메서드
     * @param id 사용자 ID
     * @param oldEmail 기존 이메일
     * @param newEmail 새로운 이메일
     * @throws ResponseStatusException 401 UNAUTHORIZED - 기존 이메일이 일치하지 않을 경우
     */
    @Transactional // 변경 사항을 자동으로 DB에 반영하도록 트랜잭션 처리
    public void updateEmail(Long id, String oldEmail, String newEmail) {
        // ID로 사용자 찾기
        User findUser = userRepository.findByIdOrElseThrow(id);

        // 기존 이메일이 일치하는지 확인
        if (!findUser.getEmail().equals(oldEmail)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "이메일이 일치하지 않습니다.");
        }

        // 이메일 업데이트
        findUser.updateEmail(newEmail);
    }

    /**
     * 사용자 삭제 메서드
     * @param id 삭제할 사용자 ID
     * @throws ResponseStatusException 404 NOT FOUND - 존재하지 않는 사용자 ID 요청 시
     */
    public void delete(Long id) {
        // ID로 사용자 찾기 (없으면 예외 발생)
        User findUser = userRepository.findByIdOrElseThrow(id);

        // 사용자 삭제
        userRepository.delete(findUser);
    }
}
