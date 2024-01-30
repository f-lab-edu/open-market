package com.flab.openmarket.service;

import com.flab.openmarket.dto.MemberCreateRequestDto;
import com.flab.openmarket.model.Member;
import com.flab.openmarket.model.MemberRole;
import com.flab.openmarket.repository.MemberRepository;
import com.flab.openmarket.util.DateUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void createUser(MemberCreateRequestDto createRequestDto) {
        this.checkDuplicateEmail(createRequestDto.getEmail());
        this.checkDuplicatePhone(createRequestDto.getPhone());

        Member member = Member.builder()
                .email(createRequestDto.getEmail())
                .name(createRequestDto.getName())
                .password(passwordEncoder.encode(createRequestDto.getPasswd()))
                .phone(createRequestDto.getPhone())
                .role(MemberRole.fromValue(createRequestDto.getRole()))
                .createdAt(DateUtil.getCurrentUTCDateTime())
                .build();

        memberRepository.save(member);
    }

    @Transactional(readOnly = true)
    public void checkDuplicateEmail(String email) {
        boolean existsByEmail = memberRepository.existsByEmail(email);

        if(existsByEmail) {
            throw new RuntimeException("duplicate email");
        }
    }

    @Transactional(readOnly = true)
    public void checkDuplicatePhone(String phone) {
        boolean existsByPhone = memberRepository.existsByPhone(phone);

        if(existsByPhone) {
            throw new RuntimeException("duplicate phone");
        }
    }
}
