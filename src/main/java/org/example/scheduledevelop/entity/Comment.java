package org.example.scheduledevelop.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "comments")
public class Comment extends ScheduleDevelop{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String contents;

    @ManyToOne
    @JoinColumn(name = "schedule_id", nullable = false) // ✅ 필수값
    private Schedule schedule;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false) // ✅ 필수값
    private User user;

    // ✅ 생성자 수정
    public Comment(String contents, Schedule schedule, User user) {
        this.contents = contents;
        this.schedule = schedule;
        this.user = user;
    }

    public void updateContent(String contents) {
        this.contents = contents;
    }
}