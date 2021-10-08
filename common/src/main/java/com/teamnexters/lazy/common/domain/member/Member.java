package com.teamnexters.lazy.common.domain.member;

import javax.persistence.*;

import com.teamnexters.lazy.common.domain.BaseTimeEntity;
import lombok.*;

@Getter
@Table(name = "member")
@ToString(of = {"memIdx", "name", "email", "provider"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mem_idx")
    private Long memIdx;

    @Column(name = "oauth_id")
    private Integer oauthId;

    @Column(name = "password")
    private String password;

    @Column(name = "mem_name", nullable = false)
    private String name;

    @Setter
    @Column(name = "mem_nickname", unique = true)
    private String nickName;

    @Column(name = "mem_email", unique = true)
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
    public Member(Integer oauthId, String password, String name, String nickName, String email, String picture, Role role, Provider provider) {
        this.oauthId = oauthId;
        this.password = password;
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
