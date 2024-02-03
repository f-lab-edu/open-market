package com.flab.openmarket.repository;

import com.flab.openmarket.model.Member;
import com.flab.openmarket.model.MemberRole;
import com.flab.openmarket.util.DateUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@MybatisTest
class MemberRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("회원 아이디를 통해 회원 정보를 조회한다.")
    @Test
    void findById() {
        // given
        Member member = this.createMember("test@test.com", "memberName", "memberPasswd",
                "010111111111", MemberRole.CUSTOMER, DateUtil.getCurrentUTCDateTime());
        Long memberId = memberRepository.save(member);

        // when
        Member savedMember = memberRepository.findById(memberId);

        // then
        assertThat(savedMember).extracting("email", "name", "phone")
                .contains("test@test.com", "memberName", "010111111111");
    }

    @DisplayName("회원 정보를 저장한다.")
    @Test
    void saveMember() {
        // given
        Member member = this.createMember("test@test.com", "memberName", "memberPasswd",
                "010111111111", MemberRole.CUSTOMER, DateUtil.getCurrentUTCDateTime());

        // when
        Long memberId = memberRepository.save(member);

        // then
        assertThat(memberId).isNotNull();
    }

    @DisplayName("email을 통해 member를 조회한다.")
    @Test
    void existsByEmail() {
        // given
        String email = "member@test.com";
        Member member = this.createMember(email, "memberName", "memberPasswd",
                "010111111111", MemberRole.CUSTOMER, DateUtil.getCurrentUTCDateTime());
        memberRepository.save(member);

        // when
        boolean existsByEmail = memberRepository.existsByEmail(email);

        // then
        assertThat(existsByEmail).isTrue();
    }

    @DisplayName("phone을 통해 member를 조회한다.")
    @Test
    void existsByPhone() {
        // given
        String phone = "010111111111";
        Member member = this.createMember("member@test.com", "memberName", "memberPasswd",
                phone, MemberRole.CUSTOMER, DateUtil.getCurrentUTCDateTime());
        memberRepository.save(member);

        // when
        boolean existsByPhone = memberRepository.existsByPhone(phone);

        // then
        assertThat(existsByPhone).isTrue();
    }

    private Member createMember(String email, String name, String password, String phone, MemberRole memberRole,
                                LocalDateTime createdAt
    ) {
        return Member.builder()
                .email(email)
                .name(name)
                .password(password)
                .phone(phone)
                .role(memberRole)
                .createdAt(createdAt)
                .build();
    }

}