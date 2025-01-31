package soon.PTCMR_Back.domain.member.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String uuid;

    @Column(nullable = false)
    private String deviceToken;

    @Enumerated(EnumType.STRING)
    private SocialType socialType;

    public static Member create(String uuid, String name, String deviceToken, SocialType socialType) {
        return Member.builder()
            .name(name)
            .socialType(socialType)
            .uuid(uuid)
            .deviceToken(deviceToken)
            .build();
    }

    @Builder
    private Member(String name, String deviceToken, SocialType socialType, String uuid) {
        this.name = name;
        this.deviceToken = deviceToken;
        this.socialType = socialType;
        this.uuid = uuid;
    }

    public void update(String uuid, String name, SocialType socialType) {}
}
