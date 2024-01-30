package com.flab.openmarket.repository;

import com.flab.openmarket.model.Member;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberRepository {
    void save(Member member);

    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);
}
