package com.cos.jwt.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;

    private String password;
    @Enumerated(value = EnumType.STRING)
    private Authority authority;
    @Builder
    public Member(String email ,String password, Authority authority) {
        this.email = email;
        this.password = password;
        this.authority = authority;
    }
}
