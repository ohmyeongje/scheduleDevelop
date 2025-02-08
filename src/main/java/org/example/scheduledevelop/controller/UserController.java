package org.example.scheduledevelop.controller;


import lombok.RequiredArgsConstructor;
import org.example.scheduledevelop.dto.UpdateEmailRequestDto;
import org.example.scheduledevelop.dto.UserRequestDto;
import org.example.scheduledevelop.dto.UserResponseDto;
import org.example.scheduledevelop.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<UserResponseDto> signUp(@RequestBody UserRequestDto requestDto) {
        UserResponseDto userResponseDto =
                userService.signUp(
                        requestDto.getUsername(),
                        requestDto.getEmail(),
                        requestDto.getPassword()
                );

        return new ResponseEntity<>(userResponseDto, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> findByTd(@PathVariable Long id) {
        UserResponseDto userResponseDto = userService.findById(id);

        return new ResponseEntity<>(userResponseDto, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateEmail(@PathVariable Long id, @RequestBody UpdateEmailRequestDto requestDto) {
        userService.updateEmail(id, requestDto.getOldEmail(), requestDto.getNewEmail());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        userService.delete(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
