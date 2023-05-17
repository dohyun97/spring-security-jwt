package com.cos.jwt.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;

    private String password;
    @Enumerated(value = EnumType.STRING)
    private Role role;
    @Builder
    public User(String username, String password, Role role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }
}
