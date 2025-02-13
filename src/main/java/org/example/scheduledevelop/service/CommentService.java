package org.example.scheduledevelop.service;

import lombok.RequiredArgsConstructor;
import org.example.scheduledevelop.dto.CommentRequestDto;
import org.example.scheduledevelop.dto.CommentResponseDto;
import org.example.scheduledevelop.dto.CommentUpdateResponseDto;
import org.example.scheduledevelop.entity.Comment;
import org.example.scheduledevelop.entity.Schedule;
import org.example.scheduledevelop.entity.User;
import org.example.scheduledevelop.respository.CommentRepository;
import org.example.scheduledevelop.respository.ScheduleRepository;
import org.example.scheduledevelop.respository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

    @Transactional
    public CommentResponseDto saveComment(CommentRequestDto requestDto) {
        // 1️ scheduleId, userId 검증
        if (requestDto.getScheduleId() == null) {
            throw new IllegalArgumentException("Schedule ID가 null입니다.");
        }
        if (requestDto.getUserId() == null) {
            throw new IllegalArgumentException("User ID가 null입니다.");
        }

        // 2️ scheduleId로 Schedule 찾기
        Schedule schedule = scheduleRepository.findById(requestDto.getScheduleId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 일정입니다. ID: " + requestDto.getScheduleId()));

        // 3️ userId로 User 찾기
        User user = userRepository.findById(requestDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다. ID: " + requestDto.getUserId()));

        // 4️ Comment 객체 생성 (Schedule, User 포함)
        Comment comment = new Comment(requestDto.getContents(), schedule, user);
        commentRepository.save(comment);

        // 5️ ResponseDto 반환
        return new CommentResponseDto(comment.getId(), comment.getContents());
    }



    @Transactional(readOnly = true)
    public List<CommentResponseDto> findAll() {
       return commentRepository.findAll().stream()
               .map(comment -> new CommentResponseDto(comment.getId(), comment.getContents()))
               .collect(Collectors.toList());
    }

    @Transactional
    public CommentUpdateResponseDto updateComment(Long id, CommentRequestDto requestDto) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Comment not found with id: " + id));

       comment.updateContent(requestDto.getContents());
        return new CommentUpdateResponseDto(comment.getId(), comment.getContents());
    }

    public void delete(Long id) {
        commentRepository.deleteById(id);
    }
}
