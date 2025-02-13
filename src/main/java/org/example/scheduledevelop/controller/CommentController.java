package org.example.scheduledevelop.controller;

import lombok.RequiredArgsConstructor;
import org.example.scheduledevelop.dto.*;
import org.example.scheduledevelop.entity.Comment;
import org.example.scheduledevelop.service.CommentService;
import org.example.scheduledevelop.service.ScheduleService;
import org.example.scheduledevelop.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final ScheduleService scheduleService;
    private final UserService userService;

    @PostMapping("/comments")
    public ResponseEntity<CommentResponseDto> saveComment(@RequestBody CommentRequestDto requestDto) {
        CommentResponseDto commentResponseDto = commentService.saveComment(requestDto);
        return new ResponseEntity<>(commentResponseDto, HttpStatus.CREATED);
    }

    @GetMapping("/comments")
    public ResponseEntity<List<CommentResponseDto>> findAll() {
        List<CommentResponseDto> CommentResponseDtoList = commentService.findAll();
        return new ResponseEntity<>(CommentResponseDtoList, HttpStatus.OK);
    }

    @PatchMapping("/comments/{id}")
    public ResponseEntity<CommentUpdateResponseDto> updateComment(@PathVariable Long id, @RequestBody CommentRequestDto requestDto) {
        return ResponseEntity.ok(commentService.updateComment(id, requestDto));
    }

    @DeleteMapping("/comments/{id}")
    public void deleteById(@PathVariable Long id) {
        commentService.delete(id);
    }
}
