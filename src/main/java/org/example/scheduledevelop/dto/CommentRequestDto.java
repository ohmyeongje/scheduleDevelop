package org.example.scheduledevelop.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentRequestDto {
    @NotNull(message = "댓글 내용은 필수 입력값입니다.")
    private String contents;

    @NotNull(message = "Schedule ID는 필수 입력값입니다.")
    private Long scheduleId;

    @NotNull(message = "User ID는 필수 입력값입니다.")
    private Long userId;

    public CommentRequestDto(String contents, Long scheduleId, Long userId) {
        this.contents = contents;
        this.scheduleId = scheduleId;
        this.userId = userId;
    }
}
