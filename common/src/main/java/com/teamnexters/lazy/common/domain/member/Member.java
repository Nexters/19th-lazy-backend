package com.teamnexters.lazy.common.domain.member;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "mem_idx")
    private Long memberIdx;

    @Column(name = "mem_name", nullable = false)
    private String name;

    @Column(name = "mem_nickname")
    private String nickName;

    @Column(name = "mem_email", nullable = false)
    private String email;

    @Column(name = "mem_image_url", nullable = false)
    private String picture;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    @Builder
    public Member(String name, String nickName, String email, String picture, Role role) {
        this.name = name;
        this.nickName = nickName;
        this.email = email;
        this.picture = picture;
        this.role = role;
    }

    public Member update(String nickName, String picture) {
        this.nickName = nickName;
        this.picture = picture;

        return this;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }
}
