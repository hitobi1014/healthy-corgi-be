package com.hc.shared.security

import com.hc.member.infrastructure.entity.MemberEntity
import com.hc.member.infrastructure.repository.MemberRepository
import org.springframework.stereotype.Component

@Component
class MemberContext(
    private val memberRepository: MemberRepository,
) {

    // TODO 회원 정보 가져오기 추후 수정, 임시 사용
    fun getPrincipal(): MemberEntity {
        val tempMember = MemberEntity(
            loginId = "test1",
            password = "test22",
            name = "테스트이름",
            authCode = "qwert12345",
            registeredAt = "250104"
        )
        val savedMember = memberRepository.save(tempMember)
        return savedMember
    }
}