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
    private String username;

    @Column(nullable = false)
    private String taskTitle;

    @Column(columnDefinition = "longtext")
    private String taskContents;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Schedule() {

    }

    public Schedule(String taskTitle, String taskContents) {
        this.taskTitle = taskTitle;
        this.taskContents = taskContents;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
