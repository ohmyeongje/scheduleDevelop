package org.example.scheduledevelop.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "user")
public class User extends ScheduleDevelop{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //유저 아이디

    @Column(nullable = false, unique = true)
    private String username; //유저명

    @Column(nullable = false, unique = true)
    private String email; //이메일

    @Column(nullable = false)
    private String password; //비밀번호


    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public User() {}

    //
    public void updateEmail(String email) {
        this.email = email;
    }
}