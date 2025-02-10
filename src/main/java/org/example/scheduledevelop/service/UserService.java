package org.example.scheduledevelop.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.scheduledevelop.dto.UserResponseDto;
import org.example.scheduledevelop.entity.User;
import org.example.scheduledevelop.respository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserResponseDto signUp(String username, String email, String password) {

        User user = new User(username, email, password);

        User savedUser = userRepository.save(user);

        return new UserResponseDto(savedUser.getId(), savedUser.getUsername(), savedUser.getEmail(), savedUser.getPassword());
    }

    //로그인 기능 추가
    public UserResponseDto login(String email, String password, HttpServletRequest request) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "이메일 또는 비밀번호가 잘못되었습니다."));

        // 세션 생성 및 저장 (세션 키를 "user"로 변경)
        HttpSession session = request.getSession(true);
        session.setAttribute("user", user);

        log.info(" 로그인 성공! 세션 저장: {} (사용자: {})", session.getId(), user);

        return new UserResponseDto(user.getId(), user.getUsername(), user.getEmail(), user.getPassword());
    }

    public UserResponseDto findById(Long id) {

        Optional<User> optionalUser = userRepository.findById(id);

        if(optionalUser.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,  "Does not exist id = " + id);
        }
        User finduser = optionalUser.get();

        return new UserResponseDto(finduser.getId(), finduser.getUsername(), finduser.getEmail(), finduser.getPassword());
    }

    @Transactional
    public void updateEmail(Long id, String oldEmail, String newEmail) {

        User findUser = userRepository.findByIdOrElseThrow(id);

        if (!findUser.getEmail().equals(oldEmail)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "이메일이 일치하지 않습니다.");
        }

        findUser.updateEmail(newEmail);
    }

    public void delete(Long id) {

        User findUser = userRepository.findByIdOrElseThrow(id);

        userRepository.delete(findUser);
    }
}
