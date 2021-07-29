package com.teamnexters.lazy.api.service;

import com.teamnexters.lazy.api.exception.EmailDuplicationException;
import com.teamnexters.lazy.common.domain.member.Member;
import com.teamnexters.lazy.common.domain.member.MemberDto;
import com.teamnexters.lazy.common.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public MemberDto.Res create(MemberDto.SignUpReq dto) {
        if (isExistedEmail(dto.getEmail()))
            throw new EmailDuplicationException(dto.getEmail());

        Member entity = memberRepository.save(dto.toEntity());

        return new MemberDto.Res(entity);

    }


    @Transactional(readOnly = true)
    public boolean isExistedEmail(String email) {
        return memberRepository.findByEmail(email) != null;
    }

}
