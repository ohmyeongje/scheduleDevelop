package org.example.scheduledevelop.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "schedule")
public class Schedule extends ScheduleDevelop{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String taskTitle; //할 일 제목

    @Column(columnDefinition = "longtext")
    private String taskContents; //할 일 내용

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user; //사용자

    public Schedule() {

    }

    public Schedule(String taskTitle, String taskContents, User user) {
        this.taskTitle = taskTitle;
        this.taskContents = taskContents;
        this.user = user;
    }

    public void update(String newTaskTitle) {
        this.taskTitle = newTaskTitle;
    }
}