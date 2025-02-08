package org.example.scheduledevelop.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "schedule")
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String taskTitle;

    @Column(columnDefinition = "longtext")
    private String taskContents;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Schedule() {

    }

    public Schedule(String taskTitle, String taskContents, User user) {
        this.taskTitle = taskTitle;
        this.taskContents = taskContents;
        this.user = user;
    }
}