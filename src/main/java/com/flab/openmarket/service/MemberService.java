package com.flab.openmarket.service;

import com.flab.openmarket.dto.MemberCreateRequest;
import com.flab.openmarket.exception.DuplicateEmailException;
import com.flab.openmarket.exception.DuplicatePhoneException;
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
    public Long signup(MemberCreateRequest createRequestDto) {
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

        Long memberId = memberRepository.save(member);

        return memberId;
    }

    @Transactional(readOnly = true)
    public void checkDuplicateEmail(String email) {
        boolean existsByEmail = memberRepository.existsByEmail(email);

        if(existsByEmail) {
            throw new DuplicateEmailException();
        }
    }

    @Transactional(readOnly = true)
    public void checkDuplicatePhone(String phone) {
        boolean existsByPhone = memberRepository.existsByPhone(phone);

        if(existsByPhone) {
            throw new DuplicatePhoneException();
        }
    }
}
