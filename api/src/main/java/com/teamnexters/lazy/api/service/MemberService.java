package com.teamnexters.lazy.api.service;

import com.teamnexters.lazy.api.exception.EmailDuplicationException;
import com.teamnexters.lazy.common.domain.member.Member;
import com.teamnexters.lazy.common.domain.member.MemberDto;
import com.teamnexters.lazy.common.domain.member.MemberRepository;
import java.util.List;
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

    /**
     * 회원 1명 조회
     *
     * @param memIdx 회원 번호
     * @return 해당 회원 Data
     */
    @Transactional(readOnly = true)
    public Member getOneMember(Long memIdx) {
        return memberRepository.findById(memIdx).orElseThrow();
    }

    @Transactional(readOnly = true)
    public boolean isExistedEmail(String email) {
        return memberRepository.findByEmail(email).orElseGet(null) != null;
    }

}
