package com.teamnexters.lazy.common.domain.member;

import javax.persistence.*;

import com.teamnexters.lazy.common.domain.BaseTimeEntity;
import lombok.*;

@Getter
@Table(name = "member")
@ToString(of = {"name", "email"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mem_idx")
    private Long memIdx;

    @Column(name = "mem_name", nullable = false)
    private String name;

    @Setter
    @Column(name = "mem_nickname")
    private String nickName;

    @Column(name = "mem_email", nullable = false)
    private String email;

    @Column(name = "mem_image_url", nullable = false)
    private String picture;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    @Enumerated(EnumType.STRING)
    @Column(name = "provider", nullable = false)
    private Provider provider; // OAuth2 Provider

    @Builder
    public Member(String name, String nickName, String email, String picture, Role role, Provider provider) {
        this.name = name;
        this.nickName = nickName;
        this.email = email;
        this.picture = picture;
        this.role = role;
        this.provider = provider;
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
