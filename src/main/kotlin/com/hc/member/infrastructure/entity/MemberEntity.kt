package com.hc.member.infrastructure.entity

import com.hc.member.domain.enum.Role
import com.hc.member.domain.enum.Status
import com.hc.shared.common.DeleteEntity
import jakarta.persistence.*
import org.hibernate.annotations.Comment
import java.time.LocalDate

@Entity
@Table(name = "hc_member")
class MemberEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    val id: Int = 0,

    @Column(length = 50, unique = true)
    @Comment("로그인ID")
    var loginId: String? = null,

    @Comment("비밀번호")
    var password: String? = null,

    @Column(length = 30)
    @Comment("이름")
    var name: String,

    @Comment("프로필 URL")
    var profileImageUrl: String? = null,

    @Column(length = 10, unique = true)
    @Comment("회원 인증 코드")
    val authCode: String, // 시스템에서 생성

    @Comment("생일")
    var birthday: LocalDate? = null,

    @Comment("상태")
    var status: Status = Status.PENDING,

    @Comment("회원구분")
    val role: Role = Role.MEMBER,

    /*
    회원이 들어온 날짜, 운영자가 직접 수기로 작성 yyMMdd (ex. 241215)
    -> 가입을 먼저 했어도 나중에 등록가능성이 있기에 수기로 작성 받음
     */
    @Comment("가입일자")
    val registeredAt: String,
) : DeleteEntity() {

    fun signupMember(loginId: String, password: String, birthday: LocalDate?) {
        this.loginId = loginId
        this.password = password
        this.birthday = birthday
        this.status = Status.ACTIVE
    }
}