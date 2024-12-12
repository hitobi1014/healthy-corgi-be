package com.hc.member.infrastructure.entity

import com.hc.member.domain.enum.Status
import com.hc.shared.common.DeleteEntity
import jakarta.persistence.*
import org.hibernate.annotations.Comment
import java.time.LocalDate

@Entity
@Table(name = "hc_member")
class MemberEntity(
    @Column(length = 50, unique = true)
    @Comment("로그인ID")
    val loginId: String? = null,

    @Column(length = 20)
    @Comment("비밀번호")
    val password: String? = null,

    @Column(length = 30)
    @Comment("이름")
    val name: String? = null,

    @Comment("프로필 URL")
    val profileImageUrl: String? = null,

    @Column(length = 16, unique = true)
    @Comment("회원 인증 코드")
    val authCode: String,

    @Comment("생일")
    val birthday: LocalDate? = null,

    @Comment("상태")
    val status: Status = Status.PENDING,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    val id: Int? = null,
) : DeleteEntity()