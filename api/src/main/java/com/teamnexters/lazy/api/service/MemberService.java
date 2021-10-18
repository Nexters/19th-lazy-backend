package com.teamnexters.lazy.api.service;

import com.teamnexters.lazy.api.domain.MemberDto;
import com.teamnexters.lazy.api.exception.MemberNotFoundException;
import com.teamnexters.lazy.api.exception.NickNameDuplicationException;
import com.teamnexters.lazy.common.domain.member.Member;
import com.teamnexters.lazy.common.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    /**
     * 회원 닉네임 업데이트
     *
     * @param dto 업데이트 요청 데이터
     */
    @Transactional
    public void updateNickName(MemberDto.UpdateNickNameReq dto, Long memIdx) {
        if (isExistedNickName(dto.getNickName()))
            throw new NickNameDuplicationException(dto.getNickName());

        Member member = getOneMember(memIdx);
        member.setNickName(dto.getNickName());
        memberRepository.save(member); // 확인해보고 중복코드 제거
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


    /**
     * 닉네임 중복 검사
     *
     * @param nickName 닉네임
     * @return 중복 여부 (True = 중복)
     */
    @Transactional(readOnly = true)
    public boolean isExistedNickName(String nickName) {
        return memberRepository.findByNickName(nickName).orElse(null) != null;
    }


    /**
     * 이메일로 회원 1명 조회
     *
     * @param email 이메일
     * @return 해당 회원 Data
     */
    @Transactional(readOnly = true)
    public Member getOneMemberByEmail(String email) throws MemberNotFoundException {
        return memberRepository.findByEmail(email).orElseThrow();
    }

}
