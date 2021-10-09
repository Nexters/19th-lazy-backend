package com.teamnexters.lazy.common.domain.member;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    /**
     * 이미 등록된 email 인지 검사
     *
     * @param email 이메일
     * @return 일치하는 회원 정보
     */
    Optional<Member> findByEmail(String email);


    /**
     * 이미 등록된 닉네임인지 검사
     *
     * @param  nickName 닉네임
     * @return 일치하는 회원 정보
     */
    Optional<Member> findByNickName(String nickName);

    /**
     * OAuth 서버 정보로 회원 찾기
     *
     * @param oauthId  소셜 서버 id
     * @param provider 소셜 제공자
     * @return 일치하는 회원 정보
     */
    Optional<Member> findByOauthIdAndProvider(Integer oauthId, Provider provider);

}
