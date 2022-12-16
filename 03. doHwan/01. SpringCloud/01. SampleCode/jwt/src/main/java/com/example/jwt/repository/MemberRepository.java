package com.example.jwt.repository;

import com.example.jwt.entity.Member;
import org.springframework.data.repository.CrudRepository;

public interface MemberRepository extends CrudRepository<Member, Long> {
    Member findByLoginId(String loginId);
}
