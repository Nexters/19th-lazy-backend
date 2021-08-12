package com.teamnexters.lazy.api.config.auth.dto;

import com.teamnexters.lazy.common.domain.member.Member;
import java.io.Serializable;
import lombok.Getter;

/**
 * 직렬화 기능을 가진 Member 클래스
 */
@Getter
public class SessionUser implements Serializable {

    private static final long serialVersionUID = 8554849382962837849L;

    private final String name;
    private final String email;
    private final String picture;

    public SessionUser(Member member){
        this.name = member.getName();
        this.email = member.getEmail();
        this.picture = member.getPicture();
    }
}