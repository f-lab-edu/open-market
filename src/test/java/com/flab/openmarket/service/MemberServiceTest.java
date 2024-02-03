package com.flab.openmarket.service;

import com.flab.openmarket.dto.MemberCreateRequest;
import com.flab.openmarket.exception.DuplicateEmailException;
import com.flab.openmarket.exception.DuplicatePhoneException;
import com.flab.openmarket.model.Member;
import com.flab.openmarket.model.MemberRole;
import com.flab.openmarket.repository.MemberRepository;
import com.flab.openmarket.util.DateUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
class MemberServiceTest {
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberService memberService;


    @DisplayName("사용자 입력 정보를 받아 member를 생성한다.")
    @Test
    void signup() {
        // given
        MemberCreateRequest memberCreateRequest = MemberCreateRequest.builder()
                                                                            .email("member1@test.com")
                                                                            .passwd("memberPasswd")
                                                                            .name("member1")
                                                                            .phone("01011111111")
                                                                            .role("customer")
                                                                            .build();

        // when
        Long memberId = memberService.signup(memberCreateRequest);
        Member member = memberRepository.findById(memberId);

        // then
        assertThat(member).isNotNull();
        assertThat(member.getMemberId()).isGreaterThan(0L);
        assertThat(member)
                .extracting("email", "name")
                .contains("member1@test.com", "member1");
    }

    @DisplayName("이미 가입된 이메일로 이메일 중복 검사를 하면 예외가 발생한다.")
    @Test
    void checkDuplicateEmailWithAlreadySaved() {
        // given
        String duplicatedEmail = "member1@test.com";
        Member member = this.createMember(duplicatedEmail, "memberName1", "memberPasswd1",
                "01011111111", MemberRole.CUSTOMER, DateUtil.getCurrentUTCDateTime());
        memberRepository.save(member);

        // when && then
        assertThatThrownBy(() -> memberService.checkDuplicateEmail(duplicatedEmail))
                .isInstanceOf(DuplicateEmailException.class);
    }

    @DisplayName("가입되지 않은 이메일로 이메일 중복 검사를 하면 예외가 발생하지 않는다.")
    @Test
    void checkDuplicateEmailWithNotSaved() {
        // given
        Member member = this.createMember("member1@test.com", "memberName1", "memberPasswd1",
                "01011111111", MemberRole.CUSTOMER, DateUtil.getCurrentUTCDateTime());
        memberRepository.save(member);

        // when
        String newEmail = "member2@test.com";

        // then
        assertDoesNotThrow(() -> memberService.checkDuplicateEmail(newEmail));
    }

    @DisplayName("이미 가입된 핸드폰 번호로 중복 검사를 하면 하면 예외가 발생한다.")
    @Test
    void checkDuplicatePhoneWithAlreadySaved() {
        // given
        String duplicatedPhone = "01011111111";
        Member member = this.createMember("member1@test.com", "memberName1", "memberPasswd1", duplicatedPhone,
                MemberRole.CUSTOMER, DateUtil.getCurrentUTCDateTime());
        memberRepository.save(member);

        // when && then
        assertThatThrownBy(() -> memberService.checkDuplicatePhone(duplicatedPhone))
                .isInstanceOf(DuplicatePhoneException.class)
                .hasMessage("이미 가입된 전화번호 입니다.");
    }

    @DisplayName("가입되지 않은 핸드폰 번호로 중복 검사를 하면 하면 예외가 발생하지 않는다.")
    @Test
    void checkDuplicatePhoneWithNotSaved() {
        // given
        Member member = this.createMember("member1@test.com", "memberName1", "memberPasswd1", "01011111111",
                MemberRole.CUSTOMER, DateUtil.getCurrentUTCDateTime());
        memberRepository.save(member);

        // when
        String newPhone = "01022222222";

        // then
        assertDoesNotThrow(() -> memberService.checkDuplicatePhone(newPhone));
    }

    private Member createMember(String email, String name, String password, String phone, MemberRole memberRole,
                                LocalDateTime now
    ) {
        return Member.builder()
                .email(email)
                .name(name)
                .password(password)
                .phone(phone)
                .role(memberRole)
                .createdAt(now)
                .build();
    }

}