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

    public User() {

    }

    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }


    public void updateEmail(String Email) {
        this.email = email;
    }
}
