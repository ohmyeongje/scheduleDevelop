package org.example.scheduledevelop.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class UserRequestDto {

    @NotBlank(message = "이름은 필수 입력 값입니다")
    private final String username;

    @NotBlank(message = "이메일은 필수 입력 값입니다")
    @Email(message = "유효한 이메일 주소를 입력하세요")
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$", message = "이메일 형식이 올바르지 않습니다")
    private final String email;

    @NotBlank(message = "비밀번호는 필수 입력 값입니다")
    private final String password;

    public UserRequestDto(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
}
