package com.cos.jwt.Repository;

import com.cos.jwt.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<User,Long> {
    User findByUsername(String username);
}
